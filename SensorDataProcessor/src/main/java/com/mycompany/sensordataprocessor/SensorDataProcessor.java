/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.sensordataprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.IOException;

public class SensorDataProcessor {

    // Sensor data and limits.
    public double[][][] data;
    public double[][] limit;

    // Constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
    this.data = data;
    this.limit = limit;
    }

    // Calculates average of sensor data
    private double calculateAverage(double[] array) {
        
        double result = 0;

        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }

        return result / array.length;
    }

    // Calculate data
    public void calculate(double d) {

        double[][][] processedData = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter out;

        // Write racing stats data into a file
        try {
                out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));

                for (int i = 0; i < data.length; i++) {

                    for (int j = 0; j < data[0].length; j++) {

                        for (int k = 0; k < data[0][0].length; k++) {

                            processedData[i][j][k] = data[i][j][k] / d - Math.pow(limit[i][j], 2.0);

                            if (calculateAverage(processedData[i][j]) > 10 && calculateAverage(processedData[i][j]) < 50)
                            break;

                            else if (Math.max(data[i][j][k], processedData[i][j][k]) > data[i][j][k])
                            break;

                            else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(processedData[i][j][k]), 3) && calculateAverage(data[i][j]) < processedData[i][j][k] && (i + 1) * (j + 1) > 0)
                            processedData[i][j][k] *= 2;

                            else
                            continue;
                        }

                    }
                }

                for (int i = 0; i < processedData.length; i++) {

                    for (int j = 0; j < processedData[0].length; j++) {

                    out.write(processedData[i][j] + "\t");
                    
                    }
                }
                
                out.close();
        } 
            
                catch (IOException e) {
                    e.printStackTrace();   
                }

    }
}
