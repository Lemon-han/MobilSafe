package com.emma.mobilesafe.utils.firework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class FireworkView extends View {

	private final String TAG = this.getClass().getSimpleName();
	private EditText mEditText;
	private LinkedList<Firework> fireworks = new LinkedList<>();

	public FireworkView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FireworkView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FireworkView(Context context) {
		super(context);
	}

	public void bindEditText(EditText editText) {

		// this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

		this.mEditText = editText;
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1,
					int i2) {
				/*
				 * iΪEditText����ַ�����i1Ϊ���ٵ��ַ�����i2Ϊ���ӵ��ַ�����
				 * ����launch�ĵ�����������������ķ���1Ϊ�����ұߣ�-1Ϊ��ߡ�
				 */
				float[] coordinate = getCursorCoordinate();
				launch(coordinate[0], coordinate[1], i1 == 0 ? -1 : 1);
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}

		});
	}

//	public void removeBind() {
//		mEditText.removeTextChangedListener(mTextWatcher);
//		mEditText = null;
//
//	}

	// ~~~~~~~~~~~~~private method~~~~~~~~~~~~~~~~~~~

	private void launch(float x, float y, int direction) {
		final Firework firework = new Firework(new Firework.Location(x, y),
				direction);
		firework.addAnimationEndListener(new Firework.AnimationEndListener() {
			@Override
			public void onAnimationEnd() {
				// �����������firework�Ƴ�����û��fireworkʱ����ˢ��ҳ��
				fireworks.remove(firework);
			}
		});
		fireworks.add(firework);
		firework.fire();
		invalidate();
	}

	/**
	 * @return the coordinate of cursor. x=float[0]; y=float[1];
	 */
	private float[] getCursorCoordinate() {
		/*
		 * ����ͨ�������ȡ���cursor�����ꡣ
		 * ���ȹ۲쵽TextView��invalidateCursorPath()���������ǹ������ʱ�ػ�ķ�����
		 * ����������и�invalidate(bounds.left + horizontalPadding, bounds.top +
		 * verticalPadding, bounds.right + horizontalPadding, bounds.bottom +
		 * verticalPadding);������ػ�������ɴ˿ɵõ���������
		 * �����������TextView.mEditor.mCursorDrawable��
		 * �����Drawable֮����getBounds()�õ�Rect�� ֮��Ҫ���ƫ����������ͨ����������������ã�
		 * getVerticalOffset(),getCompoundPaddingLeft(),getExtendedPaddingTop()��
		 */

		int xOffset = 0;
		int yOffset = 0;
		Class<?> clazz = EditText.class;
		clazz = clazz.getSuperclass();
		try {
			Field editor = clazz.getDeclaredField("mEditor");
			editor.setAccessible(true);
			Object mEditor = editor.get(mEditText);
			Class<?> editorClazz = Class.forName("android.widget.Editor");
			Field drawables = editorClazz.getDeclaredField("mCursorDrawable");
			drawables.setAccessible(true);
			Drawable[] drawable = (Drawable[]) drawables.get(mEditor);

			Method getVerticalOffset = clazz.getDeclaredMethod(
					"getVerticalOffset", boolean.class);
			Method getCompoundPaddingLeft = clazz
					.getDeclaredMethod("getCompoundPaddingLeft");
			Method getExtendedPaddingTop = clazz
					.getDeclaredMethod("getExtendedPaddingTop");
			getVerticalOffset.setAccessible(true);
			getCompoundPaddingLeft.setAccessible(true);
			getExtendedPaddingTop.setAccessible(true);
			if (drawable != null) {
				Rect bounds = drawable[0].getBounds();
				Log.d(TAG, bounds.toString());
				xOffset = (int) getCompoundPaddingLeft.invoke(mEditText)
						+ bounds.left;
				yOffset = (int) getExtendedPaddingTop.invoke(mEditText)
						+ (int) getVerticalOffset.invoke(mEditText, false)
						+ bounds.bottom;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		float x = mEditText.getX() + xOffset;
		float y = mEditText.getY() + yOffset;

		return new float[] { x, y };
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < fireworks.size(); i++) {
			fireworks.get(i).draw(canvas);
		}
		if (fireworks.size() > 0)
			invalidate();
	}

}