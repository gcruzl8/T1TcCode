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
    State state;
    DcMotor leftDrive, rightDrive;

    @Override
    public void init() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        state = State.START;
        timeElapsed = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        switch (state){
            case START:

                break;
            case DRIVE:

                break;
            case PART:

                break;
            case DONE:

                break;

        }

    }

    // sum functions to help set things up

    void setDaDrive(double leftDrivePower, double rightDrivePower){
        leftDrive.setPower(leftDrivePower);
        rightDrive.setPower(rightDrivePower);
    }
    void nextState(State next){
        state = next;
        timeElapsed = System.currentTimeMillis();
    }

    void stopBot(){
       setDaDrive(0, 0);
    }

    boolean timePassed(long ms) {
        return System.currentTimeMillis() - timeElapsed >= ms;
    }
    
}
