package com.sangbill.kafka.flink.consumer;

import java.util.Map;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sangbill.kafka.config.KafkaConsumerConfig;

@Component
public class ReadFromKafka {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${kafka.consumer.topic.flinkInput}")
	private String flinkInput;

	@Autowired
	private KafkaConsumerConfig config;

	public void execute() throws Exception {
		logger.info("=============readFromKafka execute!=============");
		
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		Map<String, String> propsMap = config.consumerConfigOfString();
		propsMap.put("topic", flinkInput);

		ParameterTool parameterTool = ParameterTool.fromMap(propsMap);
		FlinkKafkaConsumer010<String> consumer010 = new FlinkKafkaConsumer010<String>(
				parameterTool.getRequired("topic"), new SimpleStringSchema(), parameterTool.getProperties());
		DataStream<String> text = env.addSource(consumer010);
		// parse the data, group it, window it, and aggregate the counts
		DataStream<WordWithCount> windowCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
			@Override
			public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
				for (String word : value.split("\\s")) {
					out.collect(new WordWithCount(word, 1L));
				}
			}
		}).keyBy("word").timeWindow(Time.seconds(5), Time.seconds(1)).reduce(new ReduceFunction<WordWithCount>() {
			@Override
			public WordWithCount reduce(WordWithCount a, WordWithCount b) {
				return new WordWithCount(a.word, a.count + b.count);
			}
		});

		// print the results with a single thread, rather than in parallel
		windowCounts.print().setParallelism(1);

		env.execute("Socket Window WordCount");
	}

	// Data type for words with count
	public static class WordWithCount {

		public String word;
		public long count;

		public WordWithCount() {
		}

		public WordWithCount(String word, long count) {
			this.word = word;
			this.count = count;
		}

		@Override
		public String toString() {
			return word + " : " + count;
		}
	}

}
