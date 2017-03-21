package br.com.jonathan.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.jonathan.adapter.Adapter;
import br.com.jonathan.adapter.IAdapter;
import br.com.jonathan.soap.executer.ISoapExecuter;
import br.com.jonathan.soap.executer.SoapExecuter;
import br.com.jonathan.soap.parse.ISoapParser;
import br.com.jonathan.soap.parse.SoapParser;

@Configuration
public class FactoryBean {

	@Bean
	public ISoapParser soapParse() {
		return new SoapParser();
	}

	@Bean
	public IAdapter adapter() {
		return new Adapter();
	}

	@Bean
	public ISoapExecuter executer() {
		return new SoapExecuter();
	}

}