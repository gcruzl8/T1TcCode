package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;
@Autonomous
public class itsnotunusual extends OpMode {

    enum State {
        START,
        DRIVE,
        PART,
        DONE
    }
    long timeElapsed;
    DcMotor leftDrive, rightDrive;

    @Override
    public void init() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        State state = State.START;
        timeElapsed = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        switch (state){
            case START:

        }

    }

    // sum functions to help set things up

    void setDaDrive(double leftDrivePower, double rightDrivePower){
        leftDrive.setPower(leftDrivePower);
        rightDrive.setPower(rightDrivePower);
    }
    void nextState(State next){
        state = next;
    }

    void stopBot(){
       setDaDrive(0, 0);
    }
    
    
}
