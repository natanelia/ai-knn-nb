package com.ai_learning;

public class Main {
    public static void main(String[] args) {
        App app =  new App();
        int AttributeColumn = Integer.parseInt(args[3]);
        app.run(args[0], args[1], args[2], AttributeColumn);
    }
}
