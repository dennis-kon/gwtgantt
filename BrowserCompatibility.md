gwt-gantt uses an inline SVG element to draw lines and arrows on the screen. Inline SVG elements are part of the HTML5 spec, and therefore only work in the newest browser versions. At the time this wiki article was written, gwt-gantt works with:
  * Firefox 4 beta
  * Google Chrome 6+

Our goal is to support all HTML5 compliant browsers:
  * Firefox 4
  * Chrome 6
  * Safari 5
  * Internet Explorer 9


That being said, I created the `GanttWeekDisplayNoSVGImpl` class and substituted using GWT's deferred binding feature. This implementation renders lines as DIVs, as opposed to SVG elements. Initial tests were very promising, as I was able to view the Gantt Chart in IE8. I assume it would also work in Opera, Firefox 3.x, etc.