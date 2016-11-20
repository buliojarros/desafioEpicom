package com.epicom;

import com.epicom.Repository.SkuRepository;
import com.epicom.controller.SkuController;
import com.epicom.model.Notificacao;
import com.epicom.model.Parametros;
import com.epicom.model.Sku;
import com.epicom.util.HttpEpicomConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.Entity;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DesafioApplicationTests {

	@Mock
	SkuRepository skuRepository;

	private SkuController skuController;

	@Before
	public void setUp(){
		skuController = new SkuController(skuRepository);
	}

	@Test
	public void getSkusTest_AllValid_ResponseOk()	{
		Sku sku = new Sku(100, 200);
		when(skuRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(sku)));
		ResponseEntity<Collection<Sku>> response = skuController.getSkus();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().iterator().next(),sku);
	}

	@Test
	public void getSkusTest_EmptyResult_ResponseOk()	{
		when(skuRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList()));
		ResponseEntity<Collection<Sku>> response = skuController.getSkus();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	public void getSkusPriceTest_SomeValid_ResponseOk()	{
		Sku sku = new Sku(322360, 270231);
		Sku sku2 = new Sku(322359, 270230);
		when(skuRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(sku,sku2)));
		ResponseEntity<Collection<Sku>> response = skuController.getSkusPrice();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().iterator().next(),sku);
		assertEquals(response.getBody().size(),1);
	}

	@Test
	public void getSkusPriceTest_EmptyResult_ResponseOk()	{
		when(skuRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList()));
		ResponseEntity<Collection<Sku>> response = skuController.getSkusPrice();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	public void getSkuTest_AllValid_ResponseOk()	{
		Sku sku = new Sku(100, 200);
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(sku);
		ResponseEntity<?> response = skuController.getSku(100);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(),sku);
	}

	@Test
	public void getSkuTest_EmptyResult_BadRequest()	{
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(null);
		ResponseEntity<?> response = skuController.getSku(100);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(), "SKU inexistente");
	}

	@Test
	public void deleteSkuTest_AllValid_ResponseOk()	{
		Sku sku = new Sku(100, 200);
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(sku);
		ResponseEntity<?> response = skuController.deleteSku(100);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(),"Sku deletado");
	}

	@Test
	public void deleteSkuTest_EmptyValid_BadRequest()	{
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(null);
		ResponseEntity<?> response = skuController.deleteSku(100);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(),"SKU inexistente");
	}

	@Test
	public void updateSkuTest_AllValid_ResponseOk()	{
		Sku sku = new Sku(100, 200);
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(sku);
		ResponseEntity<?> response = skuController.updateSku(100, 200);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(),sku);
	}

	@Test
	public void updateSkuTest_EmptyValid_ResponseOk()	{
		when(skuRepository.findOne(Matchers.eq(100))).thenReturn(null);
		ResponseEntity<?> response = skuController.updateSku(100, 200);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(((Sku)response.getBody()).getId().longValue(),100L);
	}

	@Test
	public void notificaSkuTest_CreateValid_ResponseOk()	{
		Notificacao notificacao = new Notificacao();
		notificacao.setTipo("criacao_sku");
		notificacao.setParametros(new Parametros());
		notificacao.getParametros().setIdProduto(200);
		notificacao.getParametros().setIdSku(100);

		ResponseEntity<?> response = skuController.notificaSku(notificacao);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(((Sku)response.getBody()).getId().longValue(),100L);
	}

	@Test
	public void notificaSkuTest_NotValid_BadRequest()	{
		Notificacao notificacao = new Notificacao();
		notificacao.setTipo("alteracao_sku");
		notificacao.setParametros(new Parametros());
		notificacao.getParametros().setIdProduto(200);
		notificacao.getParametros().setIdSku(100);

		ResponseEntity<?> response = skuController.notificaSku(notificacao);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(),"Notificação não esperada");
	}


	@Test
	public void notificaSkuTest_Empty_BadRequest()	{
		Notificacao notificacao = new Notificacao();

		ResponseEntity<?> response = skuController.notificaSku(notificacao);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(),"Notificação não esperada");
	}

	@Test
	public void notificaSkuTest_Null_BadRequest()	{
		ResponseEntity<?> response = skuController.notificaSku(null);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(),"Notificação não esperada");
	}
}
