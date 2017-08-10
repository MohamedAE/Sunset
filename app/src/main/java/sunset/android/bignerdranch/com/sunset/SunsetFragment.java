package sunset.android.bignerdranch.com.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

	//References to the elements of SunsetFragment
	private View mSceneView;
	private View mSunView;
	private View mSkyView;

	//References to colours
	private int mBlueSkyColour;
	private int mSunsetSkyColour;
	private int mNightSkyColour;

	private boolean mSunIsUp;

	public static SunsetFragment newInstance() {
		return new SunsetFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sunset, container, false);

		mSunIsUp = true;

		mSceneView = view;
		mSunView = view.findViewById(R.id.sun);
		mSkyView = view.findViewById(R.id.sky);

		Resources resources = getResources();
		mBlueSkyColour = resources.getColor(R.color.blue_sky);
		mSunsetSkyColour = resources.getColor(R.color.sunset_sky);
		mNightSkyColour = resources.getColor(R.color.night_sky);

		mSceneView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSunIsUp) {
					startSunsetAnimation();
				} else {
					startSunriseAnimation();
				}
			}
		});

		return view;
	}

	/*Rect - position and size of a view relative to its parent*/
	private void startSunsetAnimation() {
		//Get the top of this view in pixels
		float sunYStart = mSunView.getTop();
		//Get the height of this view in pixels
		float sunYEnd = mSkyView.getHeight();

		/*Create an ObjectAnimator that can animate between the given float values
		* offFloat(...) PARAMS
		* - target object
		* - property name
		* - float values (start/end points)...*/

		//Translate sun downward
		ObjectAnimator heightAnimator = ObjectAnimator
				.ofFloat(mSunView, "y", sunYStart, sunYEnd)
				.setDuration(3000);
		//Add acceleration to animation
		heightAnimator.setInterpolator(new AccelerateInterpolator());

		//Shift colour of background sky from blue to sunset
		ObjectAnimator sunsetSkyAnimator = ObjectAnimator
				.ofInt(mSkyView, "backgroundColor", mBlueSkyColour, mSunsetSkyColour)
				.setDuration(3000);
		//ArgbEvaluator enables proper colour interpolation
		sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

		//Display night sky once sun sets
		ObjectAnimator nightSkyAnimator = ObjectAnimator
				.ofInt(mSkyView, "backgroundColor", mSunsetSkyColour, mNightSkyColour)
				.setDuration(1500);
		nightSkyAnimator.setEvaluator(new ArgbEvaluator());

		//Construct AnimatorSet
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet
				.play(heightAnimator)
				.with(sunsetSkyAnimator)
				.before(nightSkyAnimator);
		animatorSet.start();

		mSunIsUp = false;
	}

	private void startSunriseAnimation() {
		//Get the height of this view
		float sunYStart = mSkyView.getHeight();
		//Get the top of this view
		float sunYEnd = 469;

		//Translate sun upward
		ObjectAnimator heightAnimator = ObjectAnimator
				.ofFloat(mSunView, "y", sunYStart, sunYEnd)
				.setDuration(3000);
		//Add acceleration to animation
		heightAnimator.setInterpolator(new AccelerateInterpolator());

		//Shift colour of background sky from sunset to blue
		ObjectAnimator sunriseSkyAnimator = ObjectAnimator
				.ofInt(mSkyView, "backgroundColor", mSunsetSkyColour, mBlueSkyColour)
				.setDuration(3000);
		//ArgbEvaluator enables proper colour interpolation
		sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

		//Display day sky before sun begins rising
		ObjectAnimator daySkyAnimator = ObjectAnimator
				.ofInt(mSkyView, "backgroundColor", mNightSkyColour, mSunsetSkyColour)
				.setDuration(1500);
		daySkyAnimator.setEvaluator(new ArgbEvaluator());

		//Construct AnimatorSet
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet
				.play(heightAnimator)
				.with(sunriseSkyAnimator)
				.after(daySkyAnimator);
		animatorSet.start();

		mSunIsUp = true;
	}

}