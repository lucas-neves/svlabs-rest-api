package br.com.svlabs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author lucasns
 * @since #1.0
 */
public class Data {
    public static final Properties p = System.getProperties();
    private final static Logger log = LoggerFactory.getLogger(Data.class.getSimpleName());
    private static final HashMap<String, String> data = new HashMap<>();

    /**
     * Grava a propriedade que deseja (sem log)
     *
     * @param nomeProperties  Nome para a propriedade
     * @param valorProperties Valor que deseja dar a propriedade
     */
    public static void set(String nomeProperties, String valorProperties) {
        remove(nomeProperties);
        data.put(nomeProperties, valorProperties);
        log.info("Gravando... " + nomeProperties + " = " + valorProperties);
    }

    /**
     * Pega a propriedade desejada
     *
     * @param nomeProperties Nome da propriedade que deseja pegar
     * @return Retorna o valor dado a propriedade anteriormente
     */
    public static String get(String nomeProperties) {
        String valor = data.get(nomeProperties);

        if (valor == null) {
            log.debug("Tentando pegar do System Properties...");
            log.debug("Properties - " + nomeProperties + "...");
            valor = p.getProperty(nomeProperties);
        }

        log.debug("Pegando valor... " + nomeProperties + " = " + valor);
        return valor;
    }

    /**
     * Pega de um arquivo os valores predefinidos para gravar como propriedade
     *
     * @param file nome do arquivo para pegar as propriedades
     */
    public static void getResourceProperties(String file) {
        try {
            InputStream resource = Data.class.getClassLoader().getResourceAsStream(file);
            p.load(resource);
        } catch (Exception e) {
            log.warn("Falha ao tentar ler as propriedades...\n" + e);
        }
    }

    /**
     * Remove a propriedade com o valor da memoria
     *
     * @param nomeProperites nome da propriedade que deseja remover
     */
    public static void remove(String nomeProperites) {
        data.remove(nomeProperites);
    }

    /**
     * Limpa completamente as propriedades
     */
    public static void clear() {
        data.clear();
    }
}