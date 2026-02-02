package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// red alliance autonomous attempt - 5'6 guy

@Autonomous
public class RedAutonomous1 extends OpMode {

    enum State {
        START,
        ROTATE,
        SHOOT,
        RETURN,

        DONE
    }

    long timeElapsed;

    State state;

    DcMotor leftFrontDrive, leftBottomDrive, rightFrontDrive, rightBottomDrive, innerTake, outerTake, shooter;



    @Override
    public void init(){
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBottomDrive = hardwareMap.get(DcMotor.class, "leftBottomDrive");
        rightBottomDrive = hardwareMap.get(DcMotor.class, "rightBottomDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        innerTake = hardwareMap.get(DcMotor.class, "innerIntake");
        outerTake = hardwareMap.get(DcMotor.class, "outerIntake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBottomDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBottomDrive.setDirection(DcMotor.Direction.FORWARD);

        state = State.START;
        timeElapsed = System.currentTimeMillis();

    }

    @Override
    public void loop(){
        switch (state){
            case START:
                setDaDrive(1, 1);
                if(timePassed(1000)){
                    stopBot();
                    nextState(State.ROTATE);
                }
                break;
            case ROTATE:
                clockwiseRotation(1);
                if(timePassed(500)){
                    stopBot();
                    clockwiseRotation(0);
                    nextState(State.SHOOT);
                }
                break;
            case SHOOT:
                shooter.setPower(1);
                intakeOn();
                if(timePassed(1500)){
                    intakeOff();
                    shooter.setPower(0);
                    stopBot();
                    nextState(State.RETURN);
                }
            case RETURN:
                setDaDrive(-1, -1);
                if(timePassed(1000)){
                    counterclockwiseRotation(1);
                    stopBot();
                    if(timePassed(1000)){
                        counterclockwiseRotation(0);
                        nextState(State.DONE);
                    }

                }
                break;
            case DONE:
                stopBot();
                counterclockwiseRotation(0);


        }

    }

    void setDaDrive(double leftPower, double rightPower){
        leftFrontDrive.setPower(leftPower);
        leftBottomDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        rightBottomDrive.setPower(rightPower);
    }

    void strafeRight(double power){
        leftFrontDrive.setPower(power);
        leftBottomDrive.setPower(-power);
        rightFrontDrive.setPower(-power);
        rightBottomDrive.setPower(power);
    }

    void strafeLeft(double power){
        leftFrontDrive.setPower(-power);
        leftBottomDrive.setPower(power);
        rightFrontDrive.setPower(power);
        rightBottomDrive.setPower(-power);
    }

    void clockwiseRotation(double power){
        leftFrontDrive.setPower(power);
        leftBottomDrive.setPower(power);
        rightFrontDrive.setPower(-power);
        rightBottomDrive.setPower(-power);
    }

    void counterclockwiseRotation(double power){
        leftFrontDrive.setPower(-power);
        leftBottomDrive.setPower(-power);
        rightFrontDrive.setPower(power);
        rightBottomDrive.setPower(power);
    }

    void intakeOn(){
        innerTake.setPower(1);
        outerTake.setPower(1);
    }

    void intakeOff(){
        innerTake.setPower(0);
        outerTake.setPower(0);
    }

    void reverseIntake(){
        innerTake.setPower(-1);
        outerTake.setPower(-1);

    }

    void stopBot(){
        setDaDrive(0, 0);
    }

    void nextState(State next){
        state = next;
        timeElapsed = System.currentTimeMillis();
    }

    boolean timePassed(long ms){
        return System.currentTimeMillis() - timeElapsed >= ms;
    }
}
