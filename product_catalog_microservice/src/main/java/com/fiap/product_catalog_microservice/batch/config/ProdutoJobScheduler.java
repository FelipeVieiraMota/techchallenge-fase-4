package com.fiap.product_catalog_microservice.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job processarProdutos;

    // Executa a cada 2 minutos
    @Scheduled(cron = "0 */2 * * * ?")
    public void executarJobAgendado() {
        try {
            final JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processarProdutos, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
