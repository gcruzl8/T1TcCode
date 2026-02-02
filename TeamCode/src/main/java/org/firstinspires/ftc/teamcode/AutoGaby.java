package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AutoGaby extends OpMode {
    private DcMotor frontLeft, frontRight, bottomLeft, bottomRight, innerTake, outerTake, shooter;
    static final double     COUNTS_PER_MOTOR_REV    = 560 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;


    enum State{
        RUN,
        INTAKE,
        PLACE,

        FINISHED
    }
    State state = State.RUN;
    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        frontRight = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        bottomLeft = hardwareMap.get(DcMotor.class, "leftBottomDrive");
        bottomRight = hardwareMap.get(DcMotor.class, "rightBottomDrive");
        innerTake = hardwareMap.get(DcMotor.class, "innerIntake");
        outerTake = hardwareMap.get(DcMotor.class, "outerIntake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        bottomLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        innerTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outerTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Starting at",  "%7d :%7d",
                frontLeft.getCurrentPosition(),
                frontRight.getCurrentPosition());
        telemetry.update();



        state = State.RUN;

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches) {
        int newfrontLeft;
        int newbottomLeft;
        int newrightfront;
        int newbottomright;





        newfrontLeft = frontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newbottomLeft = bottomLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newrightfront = frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        newbottomright = frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

        frontLeft.setTargetPosition(newfrontLeft);
        bottomLeft.setTargetPosition(newbottomLeft);
        frontRight.setTargetPosition(newrightfront);
        bottomRight.setTargetPosition(newbottomright);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(Math.abs(speed));
        bottomLeft.setPower(Math.abs(speed));
        frontRight.setPower(Math.abs(speed));
        bottomRight.setPower(Math.abs(speed));

        telemetry.addData("Running to",  " %7d :%7d ", newfrontLeft,  newrightfront);
        telemetry.addData("Currently at",  " at %7d :%7d",
                frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());
        telemetry.update();

        frontRight.setPower(0);
        frontLeft.setPower(0);
        bottomRight.setPower(0);
        bottomRight.setPower(0);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

    @Override
    public void loop() {
        telemetry.addData("Current State", state);
        switch (state) {
            case RUN:
                telemetry.addLine("Moving Forward");
                encoderDrive(DRIVE_SPEED,  48,  48);  // S1: Forward 47 Inches with 5 Sec timeout
                encoderDrive(TURN_SPEED,   12, -12);  // S2: Turn Right 12 Inches with 4 Sec timeout
                encoderDrive(DRIVE_SPEED, -24, -24);  // S3: Reverse 24 Inches with 4 Sec timeout
                telemetry.addData("Path", "Complete");
                telemetry.update();
                if (!bottomRight.isBusy()) {
                    state=State.INTAKE;

                }
                break;
            case INTAKE:
                telemetry.addLine("Collect Balls");
                if (gamepad1.b) {
                    state = State.PLACE;
                }
                break;
            case PLACE:
                telemetry.addLine("Go to Shoot");
                if (gamepad1.x) {
                    state = State.FINISHED;
                }
                break;
            default:
                telemetry.addLine("Auto Finish");

        }


    }


}
