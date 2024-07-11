package com.sdp.sudoku.io.console;

import com.sdp.sudoku.config.CFG;
import com.sdp.sudoku.config.GameDefinition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConsoleInput {
    public GameDefinition load (String[] args) {
        byte[] bytes = null;
        switch (args.length) {
            case 0: bytes = readFromConsole();     break;
            case 1: bytes = readFromFile(args[0]); break;
            case 2: bytes = selectFile();
            default: error(127, "Demasiados parametros");
        }
        return parseData(bytes);
    }
    private GameDefinition parseData(byte[] bytes) {
        GameDefinition def = new GameDefinition();
        byte[] data = preparseData(def, bytes);
        if (def.validate()) error(16, "Los datos de entrada son erroneos");
        byte[] domain = new byte[256];
        for (int i = 0; i < CFG.DOMAIN.length; i++) domain[CFG.DOMAIN[i]] = (byte) CFG.DOMAIN[i];
//        for (int i = 0; i < bytes.length; i++) if (domain[bytes[i]] != 0x0) buff.append((char) bytes[i]);

//        String str = buff.toString();
//        Integer[] data = new Integer[str.length()];
//        for (int i = 0; i < str.length(); i++) data[i] = getIndex(str.charAt(i));

        return def;
    }
    private byte[] preparseData(GameDefinition def, byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        char c;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0x0A) {
                def.lines++;
                continue;
            }
            if (Character.isLetterOrDigit((char) bytes[i])) {
                 c = Character.toUpperCase((char) bytes[i]);
                 def.addDomain(c);
                 buff.append(c);
            }
        }
        return buff.toString().getBytes();
    }

    private byte[] readFromFile(String fileIn) {
        try {
            return Files.readAllBytes(Paths.get(fileIn));
        } catch (IOException e) {
            error(127, "No se ha podido leer el fichero " + fileIn);
            return null;
        }
    }
    private byte[] readFromConsole() {
        Scanner sc = new Scanner(System.in);
        StringBuilder buff = new StringBuilder();
        System.out.println("Introduzca el sudoku. 0 = Casilla vacia, - para acabar");
        int key = 32;  // Space
        int last = 0;
        do {
            last = key;
            switch (key) {
                case 13:
                case 32: break;  // Remove espaces and CR maintain 0x0A
                default: buff.append((char) key);
            }
            try {
                key = System.in.read();
            } catch (IOException e) {
                error(127, "Error leyendo de consola");
            }
            if ((key == 13 || key == 10) && last == 10) key = -1;
        } while (key != -1);

        System.out.println("\nSudoku leido");
        return buff.toString().getBytes();
    }

    private int getIndex (char c) {
        for (int i = 0; i < CFG.DOMAIN.length; i++) if (CFG.DOMAIN[i] == c) return i;
        return 0;
    }
    private void error (int rc, String txt) {
        System.err.println(txt);
        System.exit(rc);
    }
    private byte[] selectFile() {
        System.out.print("Fichero de prueba: ");
        Scanner scanner = new Scanner(System.in);
        return readFromFile(scanner.nextLine());
    }
}
