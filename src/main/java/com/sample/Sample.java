package com.sample;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class Sample {
    public static void main(String... args) {
        Sample sample = new Sample();
        sample.createMail(Locale.JAPAN);
        sample.createMail(Locale.US);
    }

    private void createMail(Locale locale) {
        Locale.setDefault(locale);
        Context context = new Context();
        context.setVariable("mailTo", "Hanako");
        List<String> bodies = Arrays.asList("body1", "body2", "body3");
        context.setVariable("bodies", bodies);
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("resources/");
        resolver.setTemplateMode(TemplateMode.TEXT);
        TemplateEngine engine = new TemplateEngine();
        engine.addTemplateResolver(resolver);
        Path path = Paths.get(String.format("mail_%s.txt", locale.getLanguage()));
        try (Writer writer = Files.newBufferedWriter(path);) {

            engine.process("mail.template", context, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
