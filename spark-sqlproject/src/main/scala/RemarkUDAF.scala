import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

object RemarkUDAF extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = StructType(StructField("city_name",StringType)::Nil)

  override def bufferSchema: StructType = StructType(StructField("map",MapType(StringType,LongType))::StructField("total", LongType)::Nil)

  override def dataType: DataType = ???

  override def deterministic: Boolean = ???

  override def initialize(buffer: MutableAggregationBuffer): Unit = ???

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = ???

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = ???

  override def evaluate(buffer: Row): Any = ???
}
