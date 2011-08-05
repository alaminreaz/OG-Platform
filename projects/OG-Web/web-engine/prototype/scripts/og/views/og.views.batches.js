/*
 * @copyright 2009 - present by OpenGamma Inc
 * @license See distribution for license
 */
$.register_module({
    name: 'og.views.batches',
    dependencies: [
        'og.api.rest',
        'og.api.text',
        'og.common.masthead.menu',
        'og.common.routes',
        'og.common.search_results.core',
        'og.common.util.history',
        'og.common.util.ui.dialog',
        'og.common.util.ui.message',
        'og.views.common.layout',
        'og.views.common.state'
    ],
    obj: function () {
        var api = og.api,
            common = og.common,
            details = common.details,
            history = common.util.history,
            masthead = common.masthead,
            routes = common.routes,
            search,
            ui = common.util.ui,
            module = this,
            page_name = module.name.split('.').pop(),
            check_state = og.views.common.state.check.partial('/' + page_name),
            batches,
            options = {
                slickgrid: {
                    'selector': '.OG-js-search', 'page_type': 'batches',
                    'columns': [
                        {
                            id: 'ob_date', field: 'date', width: 130, cssClass: 'og-link', filter_type: 'input',
                            name: '<input type="text" placeholder="observation date" '
                                + 'class="og-js-ob_date-filter" style="width: 110px;">',
                            toolTip: 'observation date'
                        },
                        {
                            id: 'ob_time', field: 'time', width: 130, filter_type: 'input',
                            name: '<input type="text" placeholder="observation time" '
                                + 'class="og-js-ob_time-filter" style="width: 110px;">',
                            toolTip: 'observation time'
                        },
                        {
                            id: 'status', field: 'status', width: 130, name: 'Status', toolTip: 'status'

                        }
                    ]
                },
                toolbar: {
                    'default': {
                        buttons: [
                            {name: 'delete', enabled: 'OG-disabled'},
                            {name: 'new', enabled: 'OG-disabled'}
                        ],
                        location: '.OG-toolbar'
                    },
                    active: {
                        buttons: [
                            {name: 'delete', enabled: 'OG-disabled'},
                            {name: 'new', enabled: 'OG-disabled'}
                        ],
                        location: '.OG-toolbar'
                    }
                }
            },
            default_details_page = function () {
                api.text({module: 'og.views.default', handler: function (template) {
                    var layout = og.views.common.layout,
                        $html = $.tmpl(template, {
                        name: 'Batches',
                        recent_list: history.get_html('history.batches.recent') || 'no recently viewed batches'
                    });
                    $('.ui-layout-inner-center .ui-layout-header').html($html.find('> header'));
                    $('.ui-layout-inner-center .ui-layout-content').html($html.find('> section'));
                    layout.inner.close('north'), $('.ui-layout-inner-north').empty();
                    ui.toolbar(options.toolbar['default']);
                    layout.inner.resizeAll();
                }});
            },
            details_page = function (args){
                api.rest.batches.get({
                    handler: function (result) {
                        if (result.error) return alert(result.message);
                        var f = details.batch_functions, json = result.data;
                        history.put({
                            name: json.template_data.name,
                            item: 'history.batches.recent',
                            value: routes.current().hash
                        });
                        api.text({module: module.name, handler: function (template) {
                            var layout = og.views.common.layout,
                                $html = $.tmpl(template, json.template_data);
                            $('.ui-layout-inner-center .ui-layout-header').html($html.find('> header'));
                            $('.ui-layout-inner-center .ui-layout-content').html($html.find('> section'));
                            layout.inner.close('north'), $('.ui-layout-inner-north').empty();
                            f.results('.OG-batch .og-js-results', json.data.batch_results);
                            f.errors('.OG-batch .og-js-errors', json.data.batch_errors);
                            ui.message({location: '.ui-layout-inner-center', destroy: true});
                            ui.toolbar(options.toolbar.active);
                            layout.inner.resizeAll();
                        }});
                    },
                    id: args.id,
                    loading: function () {
                        ui.message({
                            location: '.ui-layout-inner-center',
                            message: {0: 'loading...', 3000: 'still loading...'}});
                    }
                });
            };
        module.rules = {
            load: {route: '/' + page_name + '/ob_date:?/ob_time:?', method: module.name + '.load'},
            load_filter: {route: '/' + page_name + '/filter:/:id?/ob_date:?/ob_time:?',
                method: module.name + '.load_filter'},
            load_batches: {route: '/' + page_name + '/:id', method: module.name + '.load_batches'}
        };
        return batches = {
            load: function (args) {
                check_state({args: args, conditions: [
                    {new_page: function (args) {
                        batches.search(args);
                        masthead.menu.set_tab(page_name);
                    }}
                ]});
                if (args.id) return;
                default_details_page();
            },
            load_filter: function (args) {
                check_state({args: args, conditions: [
                    {new_page: function () {
                        state = {filter: true};
                        batches.load(args);
                        args.id
                            ? routes.go(routes.hash(module.rules.load_batches, args))
                            : routes.go(routes.hash(module.rules.load, args));
                    }}
                ]});
                delete args['filter'];
                search.filter($.extend(args, {filter: true}));
            },
            load_batches: function (args) {
                check_state({args: args, conditions: [{new_page: batches.load}]});
                batches.details(args);
            },
            search: function (args) {
                if (!search) search = common.search_results.core();
                search.load($.extend(options.slickgrid, {url: args}));
            },
            details: details_page,
            init: function () {for (var rule in module.rules) routes.add(module.rules[rule]);},
            rules: module.rules
        };
    }
});