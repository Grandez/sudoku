package com.sdp.sudoku.io;

import com.sdp.sudoku.config.CFG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Input {
    public Integer[] load (String[] args) {
        /*
        Integer[] data100 = {1,2,3,4,5,6,7,8,9};
        Integer[] data101 = {0,2,3,4,5,6,7,8,9};
        Integer[] data102 = {8,0,3,4,5,6,7,9,1};
        Integer[] data110 = {0,0,3,4,5,6,7,8,9};
        Integer[] data111 = {0,0,3,4,0,6,7,8,9};
        Integer[] data190 = {0,0,0,0,0,0,0,0,0};
        Integer[] data200 = { 1,2,3,4,5,6,7,8,9
                             ,9,1,2,3,4,5,6,7,8
                             ,8,9,1,2,3,4,5,6,7
                             ,7,8,9,1,2,3,4,5,6
                             ,6,7,8,9,1,2,3,4,5
                             ,5,6,7,8,9,1,2,3,4
                             ,4,5,6,7,8,9,1,2,3
                             ,3,4,5,6,7,8,9,1,2
                             ,2,3,4,5,6,7,8,9,1
                             ,2,3,4,5,6,7,8,9,1};
        Integer[] data201 = { 0,2,3,4,5,6,7,8,9
                ,9,1,2,3,4,5,6,7,8
                ,8,9,1,2,3,4,5,6,7
                ,7,8,9,1,2,3,4,5,6
                ,6,7,8,9,1,2,3,4,5
                ,5,6,7,8,9,1,2,3,4
                ,4,5,6,7,8,9,1,2,3
                ,3,4,5,6,7,8,9,1,2
                ,2,3,4,5,6,7,8,9,1
                ,2,3,4,5,6,7,8,9,1};
*/
        byte[] bytes = null;
        switch (args.length) {
            case 0: bytes = readFromConsole();     break;
            case 1: bytes = readFromFile(args[0]); break;
            case 2: bytes = selectFile();
            default: error(127, "Demasiados parametros");
        }
        return parseData(bytes);
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

        while (key != 45 && key != 95 && key != -1) {
            switch (key) {
                case 10:
                case 13:
                case 32: break;
                default: buff.append((char) key);
            }
            try {
                key = System.in.read();
            } catch (IOException e) {
                error(127, "Error leyendo de consola");
            }
        }

        System.out.println("\nSudoku leido");
        return buff.toString().getBytes();
    }

    private Integer[] parseData(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        byte[] domain = new byte[256];
        for (int i = 0; i < CFG.DOMAIN.length; i++) domain[CFG.DOMAIN[i]] = (byte) CFG.DOMAIN[i];
        for (int i = 0; i < bytes.length; i++) if (domain[bytes[i]] != 0x0) buff.append((char) bytes[i]);

        String str = buff.toString();
        Integer[] data = new Integer[str.length()];
        for (int i = 0; i < str.length(); i++) data[i] = getIndex(str.charAt(i));
        return data;
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
