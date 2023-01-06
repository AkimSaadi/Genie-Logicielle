package pacman;

import core.SceneElement;

import java.io.*;
import java.net.URL;


public class PacmanFileReader {


    public static boolean[][] levelMatrixFromFile(String fileName) {
        // On essaie de lire le fichier fileName

        final int nbRow = 32;
        final int nbCol = 29;

        boolean[][] matrixCollider = new boolean[nbRow][nbCol];

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                matrixCollider[i][j] = false;
            }
        }

        InputStreamReader inputStreamReader = null;
        FileReader fileReader = null;

        BufferedReader bufferedReader = makeBufferedReader(fileName, inputStreamReader, fileReader);

        try {


            String line = bufferedReader.readLine();

            int lineNumber = 0;

            while (line != null) {

                line.toCharArray();

                for (int indexInLine = 0; indexInLine < line.length(); indexInLine += 2) {
                    if (line.charAt(indexInLine) == 'X')
                        matrixCollider[lineNumber][indexInLine / 2] = true;
                }

                line = bufferedReader.readLine();
                lineNumber++;
            }

            if (fileReader != null)
                fileReader.close();

            if (inputStreamReader != null)
                inputStreamReader.close();

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrixCollider;

    }


    private static BufferedReader makeBufferedReader(String fileName, InputStreamReader inputStreamReader, FileReader fileReader) {

        BufferedReader bufferedReader = null;

        URL resource = PacmanFileReader.class.getResource(fileName);

        if (resource == null)
            if (!fileName.startsWith("/"))
                resource = SceneElement.class.getResource("/".concat(fileName));


        if (resource != null)
            System.out.println(resource.toString());

        if (resource != null && resource.toString().startsWith("jar:")) {

            if (!fileName.startsWith("/"))
                fileName = "/".concat(fileName);


            InputStream in = PacmanFileReader.class.getResourceAsStream(fileName);

            inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);

        } else {

            try {

                File file = new File(fileName);

                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bufferedReader;
    }


    public static boolean[][] gumMatrixFromFile(String fileName) {

        final int nbRow = 32;
        final int nbCol = 29;

        boolean[][] matrixPacgum = new boolean[nbRow][nbCol];

        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                matrixPacgum[i][j] = false;
            }
        }

        InputStreamReader inputStreamReader = null;
        FileReader fileReader = null;

        BufferedReader bufferedReader = makeBufferedReader(fileName, inputStreamReader, fileReader);

        try {


            String line = bufferedReader.readLine();

            int lineNumber = 0;

            while (line != null) {

                line.toCharArray();

                for (int indexInLine = 0; indexInLine < line.length(); indexInLine += 2) {
                    if (line.charAt(indexInLine) == 'o')
                        matrixPacgum[lineNumber][indexInLine / 2] = true;
                }

                line = bufferedReader.readLine();
                lineNumber++;
            }

            if (fileReader != null)
                fileReader.close();

            if (inputStreamReader != null)
                inputStreamReader.close();

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return matrixPacgum;

    }


}
