
import com.ralph.model.TimeHeight;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
@ViewScoped
public class LineChartBean {
  private LineChartModel lineModel;
  
  @PostConstruct
  public void init() {
      List<TimeHeight> heights = new JsonReader().fetchTideTimeHeights();
      long currentUnixTime = System.currentTimeMillis() / 1000;
      
      
      
      lineModel = new LineChartModel();
      lineModel.setExtender("chartExtender(10)");
      LineChartSeries s = new LineChartSeries();
      
      for (TimeHeight current: heights) {
          
          long timeDelta = Math.abs(current.getUnixTime() - currentUnixTime);
          
          long sixHours = 6 * 60 * 60;
          // System.out.println(currentUnixTime + "\t" + current.getUnixTime() + "\t" + timeDelta + "\t" + sixHours);
          if (timeDelta < sixHours) {
            s.set(current.getIsoTime(), current.getHeight());
          }
      }
      s.setSmoothLine(true);
      s.setShowMarker(false);
      lineModel.addSeries(s);
      
      Axis y = lineModel.getAxis(AxisType.Y);
      y.setMin(0.0);
      y.setMax(7);
      y.setLabel("Height(m)");

      Axis x = lineModel.getAxis(AxisType.X);
      
      //x.setMin(0);
      //x.setMax(7);
      x.setTickInterval("1");
      x.setLabel("Time");

  }

  public LineChartModel getLineModel() {
      return lineModel;
  }
}