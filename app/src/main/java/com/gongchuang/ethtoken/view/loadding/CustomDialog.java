/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gongchuang.ethtoken.view.loadding;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongchuang.ethtoken.R;


public class CustomDialog extends Dialog {

    private static TextView tvProgress;
    private static LoadingView loadingView;

    public CustomDialog(Context context) {
        this(context, 0);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomDialog instance(Activity activity) {
        View v = View.inflate(activity, R.layout.common_progress_view, null);
        loadingView = v.findViewById(R.id.loadingView);
        tvProgress = v.findViewById(R.id.tv_progress);
//        loadingView.setColor(ContextCompat.getColor(activity, R.color.menu_bg_color));
        CustomDialog dialog = new CustomDialog(activity, R.style.loading_dialog);
        dialog.setContentView(v,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        return dialog;
    }

    public void setTvProgress(String progressTip) {
        tvProgress.setText(progressTip);
    }
}
