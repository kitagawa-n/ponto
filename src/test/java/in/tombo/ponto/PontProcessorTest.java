package in.tombo.ponto;

import org.seasar.aptina.unit.AptinaTestCase;

public class PontProcessorTest extends AptinaTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    addSourcePath("src/test/java");
  }

  public void testデフォルトの設定でクラス生成() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigDefault.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains("public static int valid()"));
  }

  public void testパッケージ名とクラス名をデフォルトから変えても動く() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase1.class);

    compile();

    String source = getGeneratedSource("com.example.TestResource");

    assertTrue(source.startsWith("package com.example;"));
    assertTrue(source.contains("public class TestResource {"));
    assertTrue(source.contains("public static int valid()"));
  }

  public void testキーのスタイルをスネークケースに() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase2.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains("public static int test_intkey_valid()"));
  }

  public void test再読み込み設定() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase3.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains(String.valueOf(Long.MAX_VALUE)));
  }

  public void testEncodingテスト() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase4.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains("Windows-31J"));
  }

  public void test予約語がクラス名に入ると落ちる() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase5.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains("public static class _import {"));
  }

  public void testBeanスタイルのテスト() throws Exception {
    PontoProcessor processor = new PontoProcessor();
    addProcessor(processor);
    addCompilationUnit(PontoConfigCase6.class);

    compile();

    String source = getGeneratedSource("in.tombo.ponto.PontoResource");

    assertTrue(source.startsWith("package in.tombo.ponto;"));
    assertTrue(source.contains("public class PontoResource {"));
    assertTrue(source.contains("public intkey getIntkey()"));
    assertTrue(source.contains("public int getValid()"));
  }
}
