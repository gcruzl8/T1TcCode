package org.firstinspires.ftc.teamcode;
/*We the people of the united states do robotically swear*/
/*𓆝 𓆟 𓆞 𓆝 𓆟*/



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * 
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear OpMode")

public class Teamcode extends LinearOpMode {

    // Declare OpMode members.
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive1 = null;
    private DcMotor leftDrive2 = null;
    private DcMotor rightDrive1 = null;
    private DcMotor rightDrive2 = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive1  = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftDrive2  = hardwareMap.get(DcMotor.class, "leftBottomDrive ");
        rightDrive1 = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightDrive2 = hardwareMap.get(DcMotor.class, "rightBottomDrive");


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower1;
            double leftpower2;
            double rightPower1;
            double rightpower2;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.



            if(gamepad1.dpad_left){
                leftPower1 = 1;
                leftpower2 = 1;
                rightPower1 = 1;
                rightpower2 = 1;
            } else {
                double drive = gamepad1.left_stick_x;
                double turn  =  gamepad1.right_stick_y;
                leftPower1    = Range.clip(drive + turn, -1.0, 1.0) ;
                leftpower2    = Range.clip(drive + turn, -1.0, 1.0);
                rightPower1   = Range.clip(drive - turn, -1.0, 1.0) ;
                rightpower2   = Range.clip(drive - turn, -1.0, 1.0);

            }
            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            leftDrive.setPower(leftPower1);
            rightDrive.setPower(rightPower2);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
