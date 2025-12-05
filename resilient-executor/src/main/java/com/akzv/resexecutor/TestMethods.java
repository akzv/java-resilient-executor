package com.akzv.resexecutor;

public class TestMethods {
    public static String okService() {
        return "OK - Servico respondeu corretamente";
    }

    public static String slowService() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Resposta lenta";
    }

    public static String faultyService() {
        throw new RuntimeException("Falha no servico");
    }

    private static int counter = 0;
    public static String unstableService() {
        counter++;
        if (counter < 3) {
            throw new RuntimeException("Instavel, falhou " + counter + " vezes");
        }
        return "Funcionou na tentativa " + counter;
    }
}