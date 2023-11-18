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
    public double[][][] sensorData;
    public double[][] sensorLimits;

    // Constructor
    public SensorDataProcessor(double[][][] sensorData, double[][] sensorLimits) {
    this.sensorData = sensorData;
    this.sensorLimits = sensorLimits;
    }

    // Calculates average of sensor data
    private double calculateAverage(double[] array) {
        // Calculate the average value of an array
        double result = 0;

        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }

        return result / array.length;
    }

    // Calculate processed data based on given divider
    public void calculate(double divider) {

        double[][][] processedData = new double[sensorData.length][sensorData[0].length][sensorData[0][0].length];
        BufferedWriter outputWriter;

        // Write racing stats data into a file
        try {
                outputWriter = new BufferedWriter(new FileWriter("RacingStatsData.txt"));

                // Iterate over sensor data dimensions
                for (int i = 0; i < sensorData.length; i++) {

                    for (int j = 0; j < sensorData[0].length; j++) {

                        for (int k = 0; k < sensorData[0][0].length; k++) {
                            // Process sensor data
                            processedData[i][j][k] = sensorData[i][j][k] / divider - Math.pow(sensorLimits[i][j], 2.0);
                            // Check conditions for processing data
                            if (calculateAverage(processedData[i][j]) > 10 && calculateAverage(processedData[i][j]) < 50)
                            break;

                            else if (Math.max(sensorData[i][j][k], processedData[i][j][k]) > sensorData[i][j][k])
                            break;

                            else if (Math.pow(Math.abs(sensorData[i][j][k]), 3) < Math.pow(Math.abs(processedData[i][j][k]), 3) && calculateAverage(sensorData[i][j]) < processedData[i][j][k] && (i + 1) * (j + 1) > 0)
                            processedData[i][j][k] *= 2;

                            else
                            continue;
                        }

                    }
                }
                // Write processed data to the file
                for (int i = 0; i < processedData.length; i++) {

                    for (int j = 0; j < processedData[0].length; j++) {

                    outputWriter.write(processedData[i][j] + "\t");
                    
                    }
                }
                // Close the BufferedWriter
                outputWriter.close();
            } 
                // Handle IOException by printing the stack trace
                catch (IOException e) {
                    e.printStackTrace();   
                }

    }
}
