/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.frc6834.genericmotor;

public class TalonSRXFields {
    // GET
    public static int GetDeviceID                   = 0;
    public static int GetInverted                   = 1;
    public static int GetBusVoltage                 = 2;
    public static int GetMotorOutputPercent         = 3;
    public static int GetMotorOutputVoltage         = 4;
    public static int GetTemperature                = 5;
    public static int GetSelectedSensorPosition     = 6;
    public static int GetSelectedSensorVelocity     = 7;
    public static int GetStatusFramePeriod          = 8;
    public static int GetStatusFrameEnhancedPeriod  = 9;
    public static int GetClosedLoopError            = 10;

    // SET
    public static int setSelectedSensorPosition     = 0;
    public static int setNeutralMode                = 1;
}