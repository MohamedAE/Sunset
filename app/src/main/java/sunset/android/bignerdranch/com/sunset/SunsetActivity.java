package sunset.android.bignerdranch.com.sunset;

import android.support.v4.app.Fragment;

public class SunsetActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return SunsetFragment.newInstance();
	}
}