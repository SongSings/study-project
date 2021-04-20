生成pojo
https://blog.csdn.net/tonydz0523/article/details/108237593?spm=1001.2014.3001.5502
``` java
import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */

packageName = "com.jun.webflux.model;"
typeMapping = [
  (~/(?i)int/)                      : "long",
  (~/(?i)float|double|decimal|real/): "double",
  (~/(?i)datetime|timestamp/)       : "java.time.LocalDateTime",
  (~/(?i)date/)                     : "java.time.LocalDate",
  (~/(?i)time/)                     : "java.time.LocalTime",
  (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
  SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
  def className = javaName(table.getName(), true)
  def fields = calcFields(table)
  new File(dir, className + ".java").withPrintWriter { out -> generate(out, className, fields,table.getName()) }
}

def generate(out, className, fields,tableName) {
  out.println "package $packageName"
  out.println ""
    out.println "import lombok.*;"    // 添加import
    out.println "import org.springframework.data.annotation.*;"
    out.println "import org.springframework.data.relational.core.mapping.Table;"
    out.println ""
    out.println ""
    out.println "@Table(\"$tableName\")"    // 添加lombok相关注释
    out.println "@Data"
  out.println "public class $className {"
  out.println ""
  fields.each() {
    if (it.annos != "") out.println "  ${it.annos}"
    if (it.name == "id") out.println "    @Id"
    out.println "    private ${it.type} ${it.name};"
    out.println ""
  }
  out.println "}"
}

def calcFields(table) {
  DasUtil.getColumns(table).reduce([]) { fields, col ->
    def spec = Case.LOWER.apply(col.getDataType().getSpecification())
    def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
    fields += [[
                 name : javaName(col.getName(), false),
                 type : typeStr,
                 annos: ""]]
  }
}

def javaName(str, capitalize) {
  def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
    .collect { Case.LOWER.apply(it).capitalize() }
    .join("")
    .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
  capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
}
```