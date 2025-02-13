package com.fiap.product_catalog_microservice.batch;

import com.fiap.product_catalog_microservice.batch.config.ProdutoDTOFieldSetMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoControllerMapper;
import com.fiap.product_catalog_microservice.controller.ProdutoDTO;
import com.fiap.product_catalog_microservice.gateway.database.jpa.entity.ProdutoEntity;
import lombok.NonNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Types;

@Configuration
public class BatchConfiguration {

    @Bean
    public Job processarProdutos(JobRepository jobRepository, Step step) {
        return new JobBuilder("importProducts", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager,
                     ItemReader<ProdutoDTO> itemReader,
                     ItemWriter<ProdutoEntity> itemWriter,
                     ItemProcessor<ProdutoDTO, ProdutoEntity> itemProcessor) {
        return new StepBuilder("step", jobRepository)
                .<ProdutoDTO, ProdutoEntity>chunk(20, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public ItemReader<ProdutoDTO> itemReader() {

        return new FlatFileItemReaderBuilder<ProdutoDTO>()
                .name("produtoItemReader")
                .resource(new ClassPathResource("produtos.csv"))
                .delimited()
                .names("nome", "descricao", "preco", "quantidadeEstoque", "categoria")
                .fieldSetMapper(new ProdutoDTOFieldSetMapper())
                .build();
    }

    @Bean
    public ItemWriter<ProdutoEntity> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProdutoEntity>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>() {
                    //Houve a necessidade de realizar a tratativa para o campo categoria, pois o mesmo é um ENUM na entidade e VARCHAR no banco
                    @Override
                    public @NonNull SqlParameterSource createSqlParameterSource(@NonNull ProdutoEntity item) {
                        return new BeanPropertySqlParameterSource(item) {
                            @Override
                            public int getSqlType(@NonNull String paramName) {
                                if (paramName.equals("categoria")) {
                                    return Types.VARCHAR;
                                }
                                return super.getSqlType(paramName);
                            }
                        };
                    }
                })
                .dataSource(dataSource)
                .sql("INSERT INTO tb_produto (nome, descricao, preco, quantidade_estoque, categoria) " +
                        "VALUES (:nome, :descricao, :preco, :quantidadeEstoque, :categoria)")
                .build();
    }

    @Bean
    public ItemProcessor<ProdutoDTO, ProdutoEntity> itemProcessor(ProdutoControllerMapper mapper) {
        return new ProdutoProcessor(mapper);
    }
}
