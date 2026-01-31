package org.firstinspires.ftc.teamcode;



/* This is my attempt in making updated strafing idea
I saw on reddit,
 hope it works - 5'6 dude
 Shoutout random guy
 on the cubers server as well
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;



import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="updatedStrafeMovement", group="Drive")


public class updatedStrafeMovement extends OpMode {

    private DcMotor frontLeft, frontRight, bottomLeft, bottomRight, innerTake, outerTake, shooter;
    private Servo servo;
    private IMU imu;


    @Override
    public void init(){

        frontLeft = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        frontRight = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        bottomLeft = hardwareMap.get(DcMotor.class, "leftBottomDrive");
        bottomRight = hardwareMap.get(DcMotor.class, "rightBottomDrive");
        innerTake = hardwareMap.get(DcMotor.class, "innerIntake");
        outerTake = hardwareMap.get(DcMotor.class, "outerIntake");
        servo = hardwareMap.get(Servo.class, "servo");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bottomLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        innerTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outerTake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot robotOrientation = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(robotOrientation));
        imu.resetYaw();
    }

    @Override
    public void loop(){
        double deadZone = 0.05;

        double y = Math.abs(gamepad1.left_stick_y) > deadZone ? -gamepad1.left_stick_y : 0;
        double x = Math.abs(gamepad1.left_stick_x) > deadZone ? gamepad1.left_stick_x : 0;
        double rx = Math.abs(gamepad1.right_stick_x) > deadZone ? gamepad1.right_stick_x : 0;

        double landSpeed = gamepad1.right_trigger > 0.2 ? 0.3 : 1.0;

        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        double heading = angles.getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        rx *= 0.8;

        double fl = rotY + rotX + rx;
        double bl = rotY - rotX + rx;
        double fr = rotY - rotX - rx;
        double br = rotY + rotX - rx;

        double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        frontLeft.setPower(fl * landSpeed);
        frontRight.setPower(fr * landSpeed);
        bottomLeft.setPower(bl * landSpeed);
        bottomRight.setPower(br * landSpeed);

        if(gamepad1.right_trigger>0.2){
            shooter.setPower(-1.0);

        }
        else if (gamepad1.right_bumper){
            shooter.setPower(1.0);
        }
        else{
            shooter.setPower(0);
        }

        if(gamepad1.left_bumper){
            innerTake.setPower(1.0);
            outerTake.setPower(1.0);
        } else if (gamepad1.left_trigger > 0.2){
            innerTake.setPower(-1.0);
            outerTake.setPower(-1.0);
        } else{
            innerTake.setPower(0);
            outerTake.setPower(0);
        }
        if(gamepad1.a){
            servo.setPosition(1);
        }
        else{
            servo.setPosition(0);
        }




    }
}
