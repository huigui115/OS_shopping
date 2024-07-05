package com.example.myapplication.util;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.AttrHelper;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.CommonDialog;

public class ProgressDialogUtil {
    private int roateNum = 0;

    CommonDialog commonDialog;
    AbilitySlice abilitySlice;

    public ProgressDialogUtil(AbilitySlice abilitySlice) {
        this.abilitySlice = abilitySlice;
        Component circleProgress = new Component(abilitySlice);
    }

    private Component drawCircleProgress(int maxRadius, int minRadius) {
        final int circleNum = 12;

        // Init the paint
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_STYLE);
        paint.setColor(Color.WHITE);

        // Init the component
        Component circleProgress = new Component(abilitySlice);
        circleProgress.setComponentSize(AttrHelper.vp2px(100, abilitySlice), AttrHelper.vp2px(100, abilitySlice));
        circleProgress.setBackground(new ShapeElement(abilitySlice, ResourceTable.Graphic_login_dialog_bg));

        // Draw the animation
        circleProgress.addDrawTask(
                (component, canvas) -> {
                    // Reset when a round
                    if (roateNum == circleNum) {
                        roateNum = 0;
                    }

                    // Rotate the canvas
                    canvas.rotate(30 * roateNum, (float) (component.getWidth() / 2), (float) (component.getHeight() / 2));
                    roateNum++;
                    int radius = (Math.min(component.getWidth(), component.getHeight())) / 2 - maxRadius;
                    float radiusIncrement = (float) (maxRadius - minRadius) / circleNum;
                    double angle = 2 * Math.PI / circleNum;

                    // Draw the small circle
                    for (int i = 0; i < circleNum; i++) {
                        float x = (float) (component.getWidth() / 2 + Math.cos(i * angle) * radius);
                        float y = (float) (component.getHeight() / 2 - Math.sin(i * angle) * radius);
                        paint.setAlpha((1 - (float) i / circleNum));
                        canvas.drawCircle(x, y, maxRadius - radiusIncrement * i, paint);
                    }

                    // Refresh the component delay
                    abilitySlice.getUITaskDispatcher()
                            .delayDispatch(
                                    circleProgress::invalidate,
                                    150);
                });
        return circleProgress;
    }

    public void showProgress(final boolean show) {
        // Instance the dialog when dialog is null
        if (commonDialog == null) {
            commonDialog = new CommonDialog(abilitySlice);

            // Get circleProgress animation
            Component circleProgress = drawCircleProgress(AttrHelper.vp2px(6, abilitySlice), AttrHelper.vp2px(3, abilitySlice));
            commonDialog
                    .setContentCustomComponent(circleProgress)
                    .setTransparent(true)
                    .setSize(
                            DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        }

        // Show or hide the dialog
        if (show) {
            commonDialog.show();
        } else {
            commonDialog.destroy();
            commonDialog = null;
        }
    }
}
