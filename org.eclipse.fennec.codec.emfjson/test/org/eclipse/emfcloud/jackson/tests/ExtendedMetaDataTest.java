package org.eclipse.emfcloud.jackson.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emfcloud.jackson.junit.model.Author;
import org.eclipse.emfcloud.jackson.junit.model.Book;
import org.eclipse.emfcloud.jackson.junit.model.ModelFactory;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.support.Utils;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class ExtendedMetaDataTest {

   @Test
   public void testNoExtendedMetaDataNames() {
      final EMFModule emfModule = new EMFModule();
      emfModule.configure(EMFModule.Feature.OPTION_USE_NAMES_FROM_EXTENDED_META_DATA, false);
      final ObjectMapper mapper = Utils.createDefaultMapper();

      final Book book = ModelFactory.eINSTANCE.createBook();
      book.setAuthorName("Friedrich Schiller");

      final Author author = ModelFactory.eINSTANCE.createAuthor();
      author.setFirstName("Friedrich");
      author.setLastName("Schiller");
      book.setAuthor(author);

      final JsonNode json = mapper.valueToTree(book);
      assertEquals("Friedrich Schiller", json.get("authorName").asString());
      assertEquals("Friedrich", json.get("author").get("firstName").asString());
      assertEquals("Schiller", json.get("author").get("lastName").asString());
   }

   @Test
   public void testDefaultExtendedMetaDataNames() {
      final ObjectMapper mapper = EMFModule.setupDefaultMapper();

      final Book book = ModelFactory.eINSTANCE.createBook();
      book.setAuthorName("Friedrich Schiller");

      final JsonNode json = mapper.valueToTree(book);
      assertEquals("Friedrich Schiller", json.get("author").asString());
   }

   @Test
   public void testExtendedMetaDataNames() {
      final EMFModule emfModule = new EMFModule();
      emfModule.configure(EMFModule.Feature.OPTION_USE_NAMES_FROM_EXTENDED_META_DATA, true);
      final ObjectMapper mapper = Utils.createDefaultMapper();

      final Book book = ModelFactory.eINSTANCE.createBook();
      book.setAuthorName("Friedrich Schiller");

      final JsonNode json = mapper.valueToTree(book);
      assertEquals("Friedrich Schiller", json.get("author").asString());
   }
}
