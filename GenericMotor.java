/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
* <h1>GenericMotor</h1>
* Wrapper for various Motor and Speed Controller devices
* for use in FIRST Robotics Java projects.
* <p>
* 
*
* @author  Ian Bowling
* @version 0.0.1
* @since   2021-2-20 
*/
public class GenericMotor
{
    //
    // Types of supported motors and speed controllers
    // -----
    // 1 : TalonSRX
    // 2 : VictorSPX
    // 3 : Spark CAN
    // 4 : Spark
    // 5 : TalonFX
    //
    public int                              DEVICE_NULL             = -1;
    public int                              DEVICE_TALONSRX         = 1;
    public int                              DEVICE_VICTOR           = 2;
    public int                              DEVICE_SPARKCAN         = 3;
    public int                              DEVICE_SPARK            = 4;
    public int                              DEVICE_TALONFX          = 5;

    //
    // Types of motor brushes, currently only in use with CANSparkMax
    // -----
    // 0 : Brushed
    // 1 : Brushless
    //
    public int                              MOTOR_TYPE_NULL         = -1;
    public int                              MOTOR_TYPE_BRUSHED      = 0;
    public int                              MOTOR_TYPE_BRUSHLESS    = 1;

    //
    // Objects and variables pertaining to the motor itself, can be ignored by end-user
    //
    private int                             channel;            // the channel corresponding to the PWM ID of the device
    private ADXRS450_Gyro                   gyroscope;          // gyroscope is dedicated to the TalonSRXs, and is intiailized automagically
    private WPI_VictorSPX                   victor_motor;       // VictorSPX object
    private WPI_TalonSRX                    talon_motor;        // TalonSRX object
    private CANSparkMax                     can_spark_motor;    // CAN Spark Max object
    private TalonFX                         talonfx_motor;      // TalonFX object
    private Spark                           spark_motor;        // Spark object
    private CANSparkMaxLowLevel.MotorType   motor_type;         // Motor type, currently only in use with CANSparkMax
    private int                             device_type;        // Type of device being used
    private boolean                         support_autonomous; // Does the device support autonomous use?

    /**
     * Method for initializing DifferentialDrive with GenericMotors
     * @param motor_one The first motor to pass (usually right)
     * @param motor_two The second motor to pass (usually left)
     * @return A DifferentialDrive between the two passed motors.
     */
    public DifferentialDrive SetUpDifferentialDrive(GenericMotor motor_one, GenericMotor motor_two)
    {
        // TODO: Figure out if this is really not possible, regardless it should not be done anyway..
        if (motor_one.device_type != motor_two.device_type) {
            throw new java.lang.Error("SetUpDifferentialDrive: Tried to set up DifferentialDrive with conflicting motor device types.");
        }

        DifferentialDrive temp;

        switch(motor_one.device_type) {
            case DEVICE_TALONSRX:
                temp = new DifferentialDrive(motor_one.talon_motor, motor_two.talon_motor);
                break;
            case DEVICE_VICTOR:
                temp = new DifferentialDrive(motor_one.victor_motor, motor_two.victor_motor);
                break;
            case DEVICE_SPARKCAN:
                temp = new DifferentialDrive(motor_one.can_spark_motor, motor_two.can_spark_motor);
                break;
            case DEVICE_SPARK:
                temp = new DifferentialDrive(motor_one.spark_motor, motor_two.spark_motor);
                break;
            case DEVICE_TALONFX:
                temp = new DifferentialDrive(motor_one.talonfx_motor, motor_two.talonfx_motor);
                break;
            default:
                break;
        }

        return temp;
    }

    /**
     * Main method, initializes the desired device and its objects
     * @param devicetype What type of device is this object used for
     * @param motortype Is this a brushed or brushless motor?
     * @param chan What PWM channel is this device plugged into?
     * @return N/A
     */
    public GenericMotor(int devicetype, int motortype, int chan)
    {
        // Copy over parameters into our object
        device_type = devicetype;
        channel     = chan;

        // motor type uses an enum, so just parse it
        switch(motortype) {
            case MOTOR_TYPE_BRUSHLESS:
                motor_type = kBrushless;
                break;
            case MOTOR_TYPE_BRUSHED:
                motor_type = kBrushed;
                break;
            default:
                break;
        }

        // parse the device_type to see which device we should initialize
        switch(device_type) {
            case DEVICE_TALONSRX:
                talon_motor = new WPI_TalonSRX(channel);
                gyroscope = new ADXRS450_Gyro();
                supports_autonomous = true;
                break;
            case DEVICE_VICTOR:
                victor_motor = new WPI_VictorSPX(channel);
                supports_autonomous = false;
                break;
            case DEVICE_SPARKCAN:
                can_spark_motor = new CANSparkMax(channel, motor_type);
                supports_autonomous = false;
                break;
            case DEVICE_SPARK:
                spark_motor = new Spark(channel);
                supports_autonomous = false;
                break;
            case DEVICE_TALONFX:
                talonfx_motor = new TalonFX(channel);
                supports_autonomous = false;
                break;
            default:
                break;
        }
    }
}