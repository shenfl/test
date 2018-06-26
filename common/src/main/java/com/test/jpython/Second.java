package com.test.jpython;

import org.python.core.*;
import org.python.modules.time.PyTimeTuple;
import org.python.modules.time.Time;
import org.python.util.PythonInterpreter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class Second {
    public static void main(String[] args) {
//        Properties props = new Properties();
////        props.put("python.home", "/usr");
//        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
//        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
//        props.put("python.import.site", "false");
//
//        Properties preprops = System.getProperties();
//        PythonInterpreter.initialize(preprops, props, new String[0]);

        PythonInterpreter interp = new PythonInterpreter();

//        interp.exec("import sys");
//        interp.exec("sys.path.append('D:/Program Files (x86)/jython2.7.0/Lib')");//jython自己的
//        interp.exec("sys.path.append('D:/Program Files (x86)/jython2.7.0/Lib/site-packages')");//jython自己的
//        interp.exec("sys.path.append('C:/Users/xuhao/Loquat')");//我们自己写的

        // 根据这样导入文件后，就可以使用文件中的函数了
//        interp.execfile("common/src/main/resources/complex.py");


        // 这样就可以使用complex函数了
//        interp.exec("import addition\n" +
//                "\n" +
//                "def complex(a,b):\n" +
//                "    return a + b");
//        PyFunction func = interp.get("complex", PyFunction.class);
//        int a = 2010;
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(i));
////            System.out.println("anwser = " + pyobj.toString());
//        }
//        System.out.println("cost: " + (System.currentTimeMillis() - start));



//        interp.exec("def complex(params):\n" +
//                "   c = 2 + 1; d = 3 + 4;\n" +
//                "   return params['a'] + params['b']\n");
//        interp.exec("def complex(params):\n" +
//                "\tif(1 == 1):\n" +
//                "\t\tprint 12\n" +  // 多层嵌套使用多个\t就好了
//                "\treturn '{1}{0}'.format(params['a'], params['b'])\n");
//        interp.exec("def complex(params):\n" +
//                "\timport time;\n" +
//                "\treturn time.time()\n"); // 使用Float.class接
//        interp.exec("def complex(params):\n" +
//                "\tprint params['b']\n" +
//                "\tif not params.has_key('c'):\n" + // 取反使用not
//                "\t\tprint 33\n" +
//                "\treturn params['b']\n"); // localtime用Integer[].class接

        //测试11
//        interp.exec("def complex(params):\n" +
//                "\tprint params\n" +
//                "\tprint params[2]\n" +
//                "\tif not params[3]:" +
//                "\t\tprint 33\n" +
//                "\timport time;\n" +
//                "\treturn time.strftime('%Y-%m-%d %H:%M:::%S', params[2])\n"); // 用string接

        // map
//        interp.exec("def complex(params):\n" +
//                "\tprint params\n" +
//                "\tdict = {'Alice': '2341', 'Beth': '9102', 'Cecil': '3258'}\n" +
//                "\treturn dict\n");

        // array
        interp.exec("def complex(params):\n" +
                "\tprint params\n" +
                "\tdict = 'klsfe,krr,ww'\n" +
                "\treturn dict.split(',')\n");

        PyFunction func = interp.get("complex", PyFunction.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {

            PyStringMap map = new PyStringMap();
            PyTuple localtime = Time.localtime(new PyFloat(1111111111L));
//            System.out.println(localtime);
            map.__setitem__("a", new PyInteger(-10));
            map.__setitem__("b", localtime);

            //测试11
            PyTuple strptime = Time.strptime("2018-01-03 11:30:20", "%Y-%m-%d %H:%M:%S");
            PyArray array = new PyArray(Object.class, 4);
            array.set(0, new PyInteger(77));
            array.set(2, strptime);
//            array.set(2, 3);


            PyObject pyobj = func.__call__(array);
            System.out.println("answer = " + pyobj.toString());

            // map
//            System.out.println(pyobj.__tojava__(Map.class));
//            Map<String, Object> obj = (Map<String, Object>)pyobj.__tojava__(Map.class);
//            System.out.println(obj.get("Beth"));

            System.out.println(pyobj instanceof PyList);
            PyList list = (PyList) pyobj;
            for (int j = 0; j < list.size(); j++) {
                Object o = list.get(j);
                System.out.println(o);
            }
        }
        System.out.println("cost: " + (System.currentTimeMillis() - start));
        PyTuple localtime = Time.localtime(new PyFloat(1111111111L));
        System.out.println(localtime);
    }
}
