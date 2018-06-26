package com.test.es.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.LeafReaderContext;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ScriptPlugin;
import org.elasticsearch.script.*;
import org.elasticsearch.search.lookup.LeafSearchLookup;
import org.elasticsearch.search.lookup.SearchLookup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * An example script plugin that adds a {@link ScriptEngineService} implementing expert scoring.
 */
public class FieldaddScriptPlugin extends Plugin implements ScriptPlugin {

    private final static Logger LOGGER = LogManager.getLogger(FieldaddScriptPlugin.class);

    @Override
    public ScriptEngineService getScriptEngineService(Settings settings) {
        return new MyExpertScriptEngine();
    }

    /**
     * An example {@link ScriptEngineService} that uses Lucene segment details to implement pure document frequency scoring.
     */
    // tag::expert_engine
    private static class MyExpertScriptEngine implements ScriptEngineService {


        @Override
        public String getType() {
            return "expert_scripts";
        }

        @Override
        public Object compile(String scriptName, String scriptSource, Map<String, String> params) {
            if ("example_add".equals(scriptSource)) {
                return scriptSource;
            }
            throw new IllegalArgumentException("Unknown script name " + scriptSource);
        }

        @Override
        @SuppressWarnings("unchecked")
        public SearchScript search(CompiledScript compiledScript, SearchLookup lookup, @Nullable Map<String, Object> vars) {

            /**
             * 校验输入参数，DSL中params 参数列表
             */
            final long inc;
            final String fieldname;
            if (vars == null || vars.containsKey("inc") == false) {
                inc = 0;
            } else {
                inc = ((Number) vars.get("inc")).longValue();
            }

            if (vars == null || vars.containsKey("fieldname") == false) {
                throw new IllegalArgumentException("Missing parameter [fieldname]");
            } else {
                fieldname = (String) vars.get("fieldname");
            }

            LOGGER.info("pp"); // 每个shard上都会执行


            return new SearchScript() {
                @Override
                public LeafSearchScript getLeafSearchScript(LeafReaderContext context) throws IOException {
                    final LeafSearchLookup leafLookup = lookup.getLeafSearchLookup(context);

                    LOGGER.info("qq"); //只有有数据的shard上会执行
                    return new LeafSearchScript() {
                        @Override
                        public void setDocument(int doc) {
                            if (leafLookup != null) {
                                leafLookup.setDocument(doc);
                            }
                        }

                        @Override
                        public double runAsDouble() {
                            long values = 0;
                            /**
                             * 获取document中字段内容
                             */
                            for (Object v : (List<?>) leafLookup.doc().get(fieldname)) {
                                values = ((Number) v).longValue() + values;
                            }
                            return values + inc;
                        }
                    };
                }

                @Override
                public boolean needsScores() {
                    return false;
                }
            };
        }

        @Override
        public ExecutableScript executable(CompiledScript compiledScript, @Nullable Map<String, Object> params) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isInlineScriptEnabled() {
            return true;
        }

        @Override
        public void close() {
        }
    }
    // end::expert_engine
}