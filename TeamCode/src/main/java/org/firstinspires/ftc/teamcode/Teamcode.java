package org.firstinspires.ftc.teamcode;
/*We the people of the united states do robotically swear*/
/*𓆝 𓆟 𓆞 𓆝 𓆟*/



import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;




@TeleOp(name="stafe", group="Linear OpMode")

public class Teamcode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive1, leftDrive2, rightDrive1, rightDrive2, innertake, outertake, shooter;

    private Servo servo;



    @Override
    public void runOpMode() {
        leftDrive1  = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftDrive2  = hardwareMap.get(DcMotor.class, "leftBottomDrive");
        rightDrive1 = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightDrive2 = hardwareMap.get(DcMotor.class, "rightBottomDrive");

        innertake = hardwareMap.get(DcMotor.class, "innerIntake");
        outertake = hardwareMap.get(DcMotor.class, "outerIntake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        servo  = hardwareMap.get(Servo.class, "servo");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive1.setDirection(DcMotor.Direction.REVERSE);
        leftDrive2.setDirection(DcMotor.Direction.REVERSE);
        rightDrive1.setDirection(DcMotor.Direction.FORWARD);
        rightDrive2.setDirection(DcMotor.Direction.FORWARD);

        innertake.setDirection(DcMotor.Direction.FORWARD);
        outertake.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.REVERSE);

        leftDrive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftDrive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double fl = y + x + rx;
            double bl = y - x + rx;
            double fr = y - x - rx;
            double br = y + x - rx;

            double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));

            leftDrive1.setPower(fl / max);
            leftDrive2.setPower(bl / max);
            rightDrive1.setPower(fr / max);
            rightDrive2.setPower(br / max);


            // intake
            if(gamepad1.left_bumper){
                innertake.setPower(1.0);
                outertake.setPower(1.0);
            } else if (gamepad1.left_trigger > 0.2){
                innertake.setPower(-1.0);
                outertake.setPower(-1.0);
            } else{
                innertake.setPower(0);
                outertake.setPower(0);
            }

            // Shoot
            if(gamepad1.right_trigger>0.2){
                shooter.setPower(-1.0);

            }
            else if (gamepad1.right_bumper){
                shooter.setPower(1.0);
            }
            else{
                shooter.setPower(0);
            }

            // servo
            if(gamepad1.a){
                servo.setPosition(1);
            }
            else if(gamepad1.b){
                servo.setPosition(0.5);
            }
            else if(gamepad1.x){
                servo.setPosition(0);
            }




            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left1 (%.2f), left2(%.2f), right1 (%.2f), right2(%.2f)", fl,bl, fr,br);
            telemetry.update();
        }
 }


}

