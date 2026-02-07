package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// red alliance autonomous attempt - 5'6 guy

@Autonomous
public class BlueAutonomous3 extends OpMode {

    enum State {
        STRAFE_LEFT,
        DRIVE_INTAKE,
        STRAFE_LEFT_2,
        ROTATE,
        SHOOT,
        RETURN_DRIVE,
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

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBottomDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBottomDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        state = State.STRAFE_LEFT;
        timeElapsed = System.currentTimeMillis();

    }

    @Override
    public void loop(){
        switch (state){
            case STRAFE_LEFT:
                strafeLeft(0.5);
                if(timePassed(500)){
                    stopBot();
                    strafeLeft(0);
                    nextState(State.DRIVE_INTAKE);
                }
                break;
            case DRIVE_INTAKE:
                setDaDrive(0.5, 0.5);
                intakeOn();
                if(timePassed(1000)){
                    intakeOff();
                    stopBot();
                    nextState(State.STRAFE_LEFT_2);
                }
                break;
            case STRAFE_LEFT_2:
                strafeLeft(0.5);
                if(timePassed(1000)){
                    stopBot();
                    strafeLeft(0);
                    nextState(State.ROTATE);
                }
                break;
            case ROTATE:
                counterclockwiseRotation(0.5);
                if(timePassed(1000)){
                    counterclockwiseRotation(0);
                    stopBot();
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
                    nextState(State.RETURN_DRIVE);
                }
                break;
            case RETURN_DRIVE:
                setDaDrive(-0.5, -0.5);
                if(timePassed(1000)){
                    stopBot();
                    nextState(State.DONE);
                }
                break;
            case DONE:
                stopBot();
                break;


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
