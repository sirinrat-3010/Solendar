import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.tl.calendar.app.CalendarDayEvent;
import com.tl.calendar.app.SolendarController;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by AMONRATS on 14/12/2558.
 */
public class SolendarView extends View{

    private SolendarController solendarController;
    private GestureDetector gestureDetector;
    private SolendarViewListener listener;
    private boolean shouldScroll = true;

    public interface SolendarViewListener{
        public void onDayClick(Date dateClicked);
        public void onMonthScroll(Date firstDayOfNewMonth);
    }
    private final GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Date onDateClicked = solendarController.onSingleTapConfirmed(e);
            invalidate();
            if(listener != null && onDateClicked != null){
                listener.onDayClick(onDateClicked);
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return solendarController.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            gestureListener.onFling(e1, e2, velocityX, velocityY);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(shouldScroll) {
                gestureListener.onScroll(e1, e2, distanceX, distanceY);
                invalidate();
            }
            return true;
        }
    };
    public SolendarView(Context context) {
        this(context, null);
    }

    public SolendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SolendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        solendarController = new SolendarController(new Paint(), new OverScroller(getContext()),
                new Rect(), attrs, getContext(),  Color.argb(255, 233, 84, 81), Color.argb(255, 64, 64, 64), Color.argb(255, 219, 219, 219));
        gestureDetector = new GestureDetector(getContext(), gestureListener);
    }
    public void setLocale(Locale locale){
        solendarController.setLocale(locale);
        invalidate();
    }
    public void setUseThreeLetterAbbreviation(boolean useThreeLetterAbbreviation) {
        solendarController.setUseWeekDayAbbreviation(useThreeLetterAbbreviation);
        invalidate();
    }
    public void drawSmallIndicatorForEvents(boolean shouldDrawDaysHeader){
        solendarController.showSmallIndicator(shouldDrawDaysHeader);
    }

    public void setDayColumnNames(String[] dayColumnNames){
        solendarController.setDayColumnNames(dayColumnNames);
    }

    public void setShouldShowMondayAsFirstDay(boolean shouldShowMondayAsFirstDay) {
        solendarController.setShouldShowMondayAsFirstDay(shouldShowMondayAsFirstDay);
        invalidate();
    }

    public void setCurrentSelectedDayBackgroundColor(int currentSelectedDayBackgroundColor) {
        solendarController.setCurrentSelectedDayBackgroundColor(currentSelectedDayBackgroundColor);
        invalidate();
    }

    public void setCurrentDayBackgroundColor(int currentDayBackgroundColor) {
        solendarController.setCurrentDayBackgroundColor(currentDayBackgroundColor);
        invalidate();
    }

    public int getHeightPerDay(){
        return solendarController.getHeightPerDay();
    }

    public void setListener(SolendarViewListener listener){
        this.listener = listener;
    }

    public Date getFirstDayOfCurrentMonth(){
        return solendarController.getFirstDayOfCurrentMonth();
    }

    public void setCurrentDate(Date dateTimeMonth){
        solendarController.setCurrentDate(dateTimeMonth);
        invalidate();
    }

    public int getWeekNumberForCurrentMonth(){
        return solendarController.getWeekNumberForCurrentMonth();
    }

    public void setShouldDrawDaysHeader(boolean shouldDrawDaysHeader){
        solendarController.setShouldDrawDaysHeader(shouldDrawDaysHeader);
    }


    @Deprecated
    public void addEvent(CalendarDayEvent event){
        addEvent(event, false);
    }


    public void addEvent(CalendarDayEvent event, boolean shouldInvalidate){
        solendarController.addEvent(event);
        if(shouldInvalidate){
            invalidate();
        }
    }

    public void addEvents(List<CalendarDayEvent> events){
        solendarController.addEvents(events);
        invalidate();
    }


    @Deprecated
    public void removeEvent(CalendarDayEvent event){
        removeEvent(event, false);
    }

    public void removeEvent(CalendarDayEvent event, boolean shouldInvalidate){
        solendarController.removeEvent(event);
        if(shouldInvalidate){
            invalidate();
        }
    }

    public void removeEvents(List<CalendarDayEvent> events){
        solendarController.removeEvent((CalendarDayEvent) events);
        invalidate();
    }

    public void removeAllEvents() {
        solendarController.removeAllEvents();
    }

    public void showNextMonth(){
        solendarController.showNextMonth();
        invalidate();
        if(listener != null){
            listener.onMonthScroll(solendarController.getFirstDayOfCurrentMonth());
        }
    }

    public void showPreviousMonth(){
        solendarController.showPreviousMonth();
        invalidate();
        if(listener != null){
            listener.onMonthScroll(solendarController.getFirstDayOfCurrentMonth());
        }
    }

    @Override
    protected void onMeasure(int parentWidth, int parentHeight) {
        super.onMeasure(parentWidth, parentHeight);
        int width = MeasureSpec.getSize(parentWidth);
        int height = MeasureSpec.getSize(parentHeight);
        if(width > 0 && height > 0) {
            solendarController.onMeasure(width, height, getPaddingRight(), getPaddingLeft());
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        solendarController.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(solendarController.computeScroll()){
            invalidate();
        }
    }

    public void shouldScrollMonth(boolean shouldDisableScroll){
        this.shouldScroll = shouldDisableScroll;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(solendarController.onTouch(event) && shouldScroll){
            invalidate();
            if(listener != null){
                listener.onMonthScroll(solendarController.getFirstDayOfCurrentMonth());
            }
            return true;
        }
        return gestureDetector.onTouchEvent(event);
    }

}

