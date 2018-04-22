package com.suman.spring.batch;

import com.suman.spring.batch.exports.StepConfigurer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer implements CommandLineRunner {

    private static final String FULL_DATA_PROCESS_JOB_NAME = "exampleJob";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepConfigurer stepConfigurer;

    public Job getJob() {
        return jobBuilderFactory.get(FULL_DATA_PROCESS_JOB_NAME)
                .incrementer(new RunIdIncrementer()).preventRestart()
                .flow(stepConfigurer.getStep()).end().build();
    }

    @Override
    public void run(String... args) throws Exception {
        stepConfigurer.createTables();
        Job job = getJob();
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate(job.getName(), new Date());
        jobLauncher.run(job, jobParametersBuilder.toJobParameters());
    }
}
