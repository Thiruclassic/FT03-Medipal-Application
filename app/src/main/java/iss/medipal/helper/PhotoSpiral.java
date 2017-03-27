package iss.medipal.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class PhotoSpiral extends ViewGroup {
  public PhotoSpiral(Context context) {
    super(context);
  }

  public PhotoSpiral(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PhotoSpiral(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onMeasure(int widthSpec, int heightSpec) {
    measureChildren(widthSpec, heightSpec);
    View first = getChildAt(0);
    int size = first.getMeasuredWidth() + first.getMeasuredHeight();
    int width = ViewGroup.resolveSize(size, widthSpec);
    int height = ViewGroup.resolveSize(size, heightSpec);
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // Assume all children are of the same dimensions
    // Order: landscape, portrait, landscape, portrait

    View first = getChildAt(0);
    final int childWidth = first.getMeasuredWidth();
    final int childHeight = first.getMeasuredHeight();

    for (int i = 0; i < getChildCount(); ++i) {
      View child = getChildAt(i);
      int x = 0;
      int y = 0;
      switch (i) {
        case 1:
          x = childWidth;
          y = 0;
          break;
        case 2:
          x = childHeight;
          y = childWidth;
          break;
        case 3:
          x = 0;
          y = childHeight;
          break;
      }
      child.layout(x, y,
          x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
    }
  }
}
