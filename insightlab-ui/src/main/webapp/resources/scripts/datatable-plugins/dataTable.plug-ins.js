;
/**
 * DATATABLE PLUG-INS
 */

$.fn.dataTableExt.afnFiltering.push(function(oSettings, aData, iDataIndex) {
    /*
     * Type:        Plugin for DataTables (www.datatables.net) JQuery plugin.
     * Description: Adds function which uses for filtering.
     * Inputs:      object:oSettings - dataTables settings object, aData - dataTables
     * Returns:     JQuery
     */
    var anControl = $(oSettings['nTableWrapper']).find('input.search')[0];
    var valid = false;
    if (anControl) {
        if (anControl.value == '') {
            return true;
        } else {
            $.each(aData, function(index, element) {
                if (element) {
                    var text = element.toString().replace(/(<([^>]+)>)/ig, '');
                    text = text.toLowerCase();
                    if (text.indexOf(anControl.value.toLowerCase()) != -1) {
                        valid = true;
                    }
                }
            });
        }
        return valid;
    }
    return true;
});

$.fn.dataTableExt.afnFiltering.push(function(oSettings, aData, iDataIndex) {
    /*
     * Type:        Plugin for DataTables (www.datatables.net) JQuery plugin.
     * Description: Adds function which uses for custom filtering.
     * Inputs:      object:oSettings - dataTables settings object, aData - dataTables
     * Returns:     JQuery
     */
    var filter = function(customFiltering) {
        if (!customFiltering || !customFiltering['value']) return true;
        if (customFiltering['type'] === 'string') {
            return (oSettings['aoData'][iDataIndex]['_aData'][customFiltering['column']].indexOf(customFiltering['value']) >= 0);
        } else if (customFiltering['type'] === 'date-range') {
            var iDateCol = customFiltering['column'];
            var filterValue = customFiltering['value'];
            var lowerDate = new Date(filterValue.substring(0, filterValue.indexOf('&&')));
            var upperDate = new Date(filterValue.substring(filterValue.indexOf('&&') + 2));
            var dateTable = new Date(new Date(aData[iDateCol].substring(0, 10)).setHours(0, 0, 0, 0));
            return lowerDate <= dateTable && dateTable <= upperDate;
        } else {
            var min = parseFloat(customFiltering['value']);
            var value = parseFloat(oSettings['aoData'][iDataIndex]['_aData'][customFiltering['column']].toString().replace(/,/g, ''));
            if (!min || value >= min) {
                return true;
            }
        }
        return false;
    };
    var customFilter = oSettings['custom-filtering'];
    if (customFilter instanceof Array) {
        for (var i = 0; i < customFilter.length; i++) {
            if (!filter(customFilter[i])) {
                return false;
            }
        }
        return true;
    } else {
        return filter(customFilter);
    }
});
/**
 * @param {Object} oSettings
 * @this {DataTable}
 * @return {DataTable}
 */
$.fn.dataTableExt.oApi.fnSetFilteringPressEnter = function(oSettings) {
    /*
     * Type:        Plugin for DataTables (www.datatables.net) JQuery plugin.
     * Name:        dataTableExt.oApi.fnSetFilteringPressEnter
     * Description: Enables filtration to be triggered by pressing the enter key instead of keyup or delay and clean up selected columns.
     * Inputs:      object:oSettings - dataTables settings object
     * Returns:     JQuery
     * Usage:       $('#example').dataTable().fnSetFilteringPressEnter();
     */
    var _that = this;
    _that.each(function(i) {
        $.fn.dataTableExt.iApiIndex = i;
        var anControl = $(_that.fnSettings()['aanFeatures']['f']).find('input[type="text"]');
        anControl.addClass('acxiom-input search span12').attr('placeholder', '查找');
        anControl.on('click', function() {
            this.select();
        });
        anControl.on('keyup', function(e) {
            //clear custom filter value on search
            if (oSettings['custom-filtering']) {
                oSettings['custom-filtering']['value'] = '';
                _that.fnFilter('');
            }
            $(_that).find('input[type="checkbox"]').prop('checked', false);
        });
        return this;
    });
    return this;
};


//$.fn.dataTableExt.oApi.fnResetFilterButton = function (oSettings) {
//    /*
//    * Type:        Plugin for DataTables (www.datatables.net) JQuery plugin.
//    * Name:        dataTableExt.oApi.fnResetFilterButton
//    * Description: Adds reset button for datatable filter.
//    * Returns:     JQuery
//    * Usage:       $('#example').dataTable().fnResetFilterButton();
//    */

//    var _that = this;
//    _that.each(function (i) {
//        $.fn.dataTableExt.iApiIndex = i;
//        var $filterContainer = $(_that.fnSettings()["aanFeatures"]["f"]);
//        var $inputFilter = $filterContainer.find('input');
//        if ($inputFilter.length) {
//            var $button = $('<input>', { type: "button", val: "Reset" }).addClass('acxiom-button action');
//            $button.prop("disabled", true);
//            $inputFilter.on('keyup', function (e) {
//                $button.prop("disabled", e.target.value === "");
//            });
//            $button.on('click', function () {
//                //clear custom filter value on search
//                if (oSettings["custom-filtering"]) {
//                    oSettings["custom-filtering"]["value"] = '';
//                }
//                $inputFilter.val('');
//                _that.fnFilter('');
//                $button.prop("disabled", true);

//            });
//            $button.appendTo($filterContainer.find('label'));
//        }
//        return this;
//    });
//    return this;
//};

/**
 * @param {Object} oSettings
 * @param {String} sCol
 * @return {number}
 */
$.fn.dataTableExt.oApi.fnGetColumnIndex = function(oSettings, sCol) {
    /*
     * Type:        Plugin for DataTables (www.datatables.net) JQuery plugin.
     * Name:        dataTableExt.oApi.fnGetColumnIndex
     * Description: Returns column index by mapped property name (mData).
     * Returns:     column index
     * Usage:       $('#example').dataTable().fnGetColumnIndex(oSettings, 'text');
     */
    var cols = oSettings['aoColumns'];
    for (var x = 0, xLen = cols.length; x < xLen; x++) {
        if (cols[x].mData === sCol) {
            return x;
        }
    }
    return -1;
};

/**
 * Return an array of table values from a particular column.
 * @param {Object} oSettings DataTable settings object.
 * @param {number} iColumn The id of the column to extract the data from.
 * @param {boolean} bUnique If set to false duplicated values are not filtered out.
 * @param {boolean} bFiltered If set to false all the table data is used (not only the filtered).
 * @param {boolean} bIgnoreEmpty If set to false empty values are not filtered from the result array.
 * @this {DataTable}
 * @return {Array.<String>}
 */
$.fn.dataTableExt.oApi.fnGetColumnData = function(oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
    var that = this;
    // check that we have a column id
    if (typeof iColumn == 'undefined') return new Array();

    // by default we only wany unique data
    if (typeof bUnique == 'undefined') bUnique = true;

    // by default we do want to only look at filtered data
    if (typeof bFiltered == 'undefined') bFiltered = true;

    // by default we do not wany to include empty values
    if (typeof bIgnoreEmpty == 'undefined') bIgnoreEmpty = true;

    // list of rows which we're going to loop through
    var aiRows;

    // use only filtered rows
    if (bFiltered == true) aiRows = oSettings['aiDisplay'];
    // use all rows
    else aiRows = oSettings['aiDisplayMaster']; // all row numbers

    // set up data array
    var asResultData = new Array();

    for (var i = 0, c = aiRows.length; i < c; i++) {
        var iRow = aiRows[i];
        var aData = that.fnGetData(iRow);
        var sValue = aData['userID'];

        // ignore empty values?
        if (bIgnoreEmpty == true && sValue.length == 0) continue;

        // ignore unique values?
        else if (bUnique == true && $.inArray(sValue, asResultData) > -1) continue;

        // else push the value onto the result data array
        else asResultData.push(sValue);
    }

    return asResultData;
};

/**
 plagin for sorting formatted numbers
 **/
$.extend($.fn.dataTableExt.oSort, {
    'formatted-num-pre': function(a) {
        a = (a === '-') ? 0 : a.replace(/[^\d\-\.]/g, '');
        return parseFloat(a);
    },

    'formatted-num-asc': function(a, b) {
        return a - b;
    },

    'formatted-num-desc': function(a, b) {
        return b - a;
    },


    'formatted-Age-asc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        var c = a.currentParam;
        var n = b.currentParam;
        var diff = mapperAge[c] - mapperAge[n];
        if (diff == 0) {
            a.segment *= 1;
            b.segment *= 1;
            return a.segment - b.segment;
        } else {
            return diff;
        }
    },

    'formatted-Age-desc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        var c = a.currentParam;
        var n = b.currentParam;
        var diff = mapperAge[n] - mapperAge[c];
        if (diff == 0) {
            a.segment *= 1;
            b.segment *= 1;
            return b.segment - a.segment;
        } else {
            return diff;
        }
    },


    'formatted-NetWorth-asc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        var c = decodeTag(a.currentParam);
        var n = decodeTag(b.currentParam);
        var diff = mapperNetWorth[c] - mapperNetWorth[n];
        if (diff == 0) {
            a.segment *= 1;
            b.segment *= 1;
            return a.segment - b.segment;
        } else {
            return diff;
        }
    },

    'formatted-NetWorth-desc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        var c = decodeTag(a.currentParam);
        var n = decodeTag(b.currentParam);
        var diff = mapperNetWorth[n] - mapperNetWorth[c];
        if (diff == 0) {
            a.segment *= 1;
            b.segment *= 1;
            return b.segment - a.segment;
        } else {
            return diff;
        }
    },

    'target-group-number-pre': function(a) {
        if (a.currentParam == 'TOTALS') {
            return a;
        }
        else {
            var number = (a.currentParam === '-') ? 0 : a.currentParam.replace(/[^\d\-\.]/g, '');
            a.currentParam = parseFloat(number);
            return a;
        }
    },

    'target-group-number-asc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        return a.currentParam - b.currentParam;
    },

    'target-group-number-desc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        return b.currentParam - a.currentParam;
    },

    'target-group-string-asc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        return ((a.currentParam < b.currentParam) ? -1 : ((a.currentParam > b.currentParam) ? 1 : 0));
    },

    'target-group-string-desc': function(a, b) {
        if (a.currentParam == 'TOTALS' || b.currentParam == 'TOTALS') return 0;
        return ((a.currentParam < b.currentParam) ? 1 : ((a.currentParam > b.currentParam) ? -1 : 0));
    },

    'name-group-sort-asc': function(a, b) {
        if (a == 'Off Target Group') a = '~';
        if (b == 'Off Target Group') b = '~';
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    'name-group-sort-desc': function(a, b) {
        if (a == 'Off Target Group') a = '!';
        if (b == 'Off Target Group') b = '!';
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});

/* Create an array with the values of all the input boxes in a column */
$.fn.dataTableExt.afnSortData['response-value'] = function(oSettings, iColumn) {
    var that = this;
    return $.map(oSettings.oApi._fnGetTrNodes(oSettings), function(element, i) {
        var sData = that.fnGetData(element);
        if (sData['fieldPosition'])
            return sData['fieldPosition'] < 10 ? '0' + sData['fieldPosition'] : sData['fieldPosition'].toString();
        return sData['responseValue'];
    });
};

/* Create an array with the values of all the input boxes in a column */
$.fn.dataTableExt.afnSortData['two-columns'] = function(oSettings, iColumn) {
    /**{@type {string]}*/
    var param = oSettings.aoColumns[iColumn].mData;
    oSettings['ext-sort'] = oSettings['ext-sort'] || { };
    oSettings['ext-sort'][param] = oSettings['ext-sort'][param] || {};
    return $.map(oSettings['aoData'], function(element, index) {
        /**{@type {Object]}*/
        var sData = element['_aData'];
        /**{@type {Object]}*/
        var currentParam = oSettings['ext-sort'][param][index] = oSettings['ext-sort'][param][index] || sData[param];
        return { currentParam: currentParam, segment: sData[oSettings['oInit']['config']['second-sort']] };
    });
};

/* Create an array with the values of all the input boxes in a column */
$.fn.dataTableExt.afnSortData['target-group'] = function(oSettings, iColumn) {
    /**{@type {string}}*/
    var param = oSettings.aoColumns[iColumn].mData;
    oSettings[param] = oSettings[param] || {};
    return $.map(oSettings['aoData'], function(element, index) {
        /**{@type {Object}}*/
        var sData = element['_aData'];
        if (sData['clusterName'] == 'TOTALS' || sData['clusterName']['currentParam'] == 'TOTALS') {
            oSettings[param][index] = oSettings[param][index] || 'TOTALS';
        } else {
            oSettings[param][index] = oSettings[param][index] || sData[param];
        }
        return { currentParam: oSettings[param][index], segment: sData[oSettings['oInit']['config']['second-sort']] };
    });
};

/* Create an array with the values of all the input boxes in a column */
$.fn.dataTableExt.afnSortData['audience-sort'] = function(oSettings, iColumn) {
    return $.map(oSettings['aoData'], function(element, i) {
        var sData = element['_aData'];
        return sData['index'].replace(/(<([^>]+)>)/ig, '');
    });
};

/**
 * Specifies Net Worth.
 * @enum {number}
 */
var mapperNetWorth = {
    '<$5K': 1,
    '<$10K': 2,
    '<$25K': 3,
    '<$50K': 4,
    '<$100K': 5,
    '<$250K': 6,
    '<$500K': 7,
    '<$999K': 8,
    '$5K-$499K': 9,
    '$10K-$499K': 10,
    '$10K-$999K': 11,
    '$25K-$499K': 12,
    '$25K-$999K': 13,
    '$50K-$499K': 14,
    '$100K-$499K': 15,
    '$100K-$999K': 16,
    '$500K-$999K': 17,
    '$1MM-$2MM': 18,
    '$2MM+': 19
};

/**
 * Specifies Age.
 * @enum {number}
 */
var mapperAge = {
    '18-23': 1,
    '18-29': 2,
    '24-29': 3,
    '24-35': 4,
    '30-35': 5,
    '30-45': 6,
    '30-55': 7,
    '36-45': 8,
    '36-55': 9,
    '36-65': 10,
    '46-55': 11,
    '46-65': 12,
    '56-65': 13,
    '66-75': 14,
    '66+': 15,
    '76+': 16

};
/**
 * @param {Object} oSettings
 * @param {String} sNewSource
 * @param {Function} fnCallback
 * @param {boolean} bStandingRedraw
 * @this {DataTable}
 */
$.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings, sNewSource, fnCallback, bStandingRedraw) {
    // DataTables 1.10 compatibility - if 1.10 then versionCheck exists.
    // 1.10s API has ajax reloading built in, so we use those abilities
    // directly.
    if ($.fn.dataTable.versionCheck) {
        var api = new $.fn.dataTable.Api(oSettings);

        if (sNewSource) {
            api.ajax.url(sNewSource).load(fnCallback, !bStandingRedraw);
        }
        else {
            api.ajax.reload(fnCallback, !bStandingRedraw);
        }
        return;
    }

    if (sNewSource !== undefined && sNewSource !== null) {
        oSettings.sAjaxSource = sNewSource;
    }

    // Server-side processing should just call fnDraw
    if (oSettings.oFeatures.bServerSide) {
        this.fnDraw();
        return;
    }

    this.oApi._fnProcessingDisplay(oSettings, true);
    var that = this;
    var iStart = oSettings._iDisplayStart;
    var aData = [];

    this.oApi._fnServerParams(oSettings, aData);

    oSettings.fnServerData.call(oSettings.oInstance, oSettings.sAjaxSource, aData, function(json) {
        /* Clear the old information from the table */
        that.oApi._fnClearTable(oSettings);

        /* Got the data - add it to the table */
        var aData = (oSettings.sAjaxDataProp !== '') ?
          that.oApi._fnGetObjectDataFn(oSettings.sAjaxDataProp)(json) : json;

        for (var i = 0; i < aData.length; i++) {
            that.oApi._fnAddData(oSettings, aData[i]);
        }

        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();

        that.fnDraw();

        if (bStandingRedraw === true) {
            oSettings._iDisplayStart = iStart;
            that.oApi._fnCalculateEnd(oSettings);
            that.fnDraw(false);
        }

        that.oApi._fnProcessingDisplay(oSettings, false);

        /* Callback user function - for event handlers etc */
        if (typeof fnCallback == 'function' && fnCallback !== null) {
            fnCallback(oSettings);
        }
    }, oSettings);
};

/**
 * @param {Object} oSettings
 * @param {String} nPaging
 * @param {Function} fnCallbackDraw
 * @this {DataTable}
 */
$.fn.dataTableExt.oPagination.input = {
    'fnInit': function(oSettings, nPaging, fnCallbackDraw) {
        var self = this;
        var $nPaging = $(nPaging);
        var $nFirst = $('<span/>').attr('class', 'paginate_button first dis-page').text(oSettings.oLanguage.oPaginate.sFirst);
        var $nPrevious = $('<span/>').attr('class', 'paginate_button previous dis-page').text(oSettings.oLanguage.oPaginate.sPrevious);
        var $nNext = $('<span/>').attr('class', 'paginate_button next').text(oSettings.oLanguage.oPaginate.sNext);
        var $nLast = $('<span/>').attr('class', 'paginate_button last').text(oSettings.oLanguage.oPaginate.sLast);
        var $nPage = $('<span/>').attr('class', 'paginate_page');
        var $nOf = $('<span/>').attr('class', 'paginate_of');
        var $nInput = $('<input/>').attr('type', 'text').css({'width': '15px', 'display': 'inline'});

        if (oSettings.sTableId !== '') {

            $nPaging.attr('id', oSettings.sTableId + '_paginate');
            $nPrevious.attr('id', oSettings.sTableId + '_previous');
            $nPrevious.attr('id', oSettings.sTableId + '_previous');
            $nNext.attr('id', oSettings.sTableId + '_next');
            $nLast.attr('id', oSettings.sTableId + '_last');
        }

        $nPaging.append($nFirst);
        $nPaging.append($nPrevious);
        $nPaging.append($nPage);
        $nPaging.append($nInput);
        $nPaging.append($nOf);
        $nPaging.append($nNext);
        $nPaging.append($nLast);

        $nFirst.click(function() {
            if (!$(this).hasClass('dis-page')) {
                oSettings.oApi._fnPageChange(oSettings, 'first');
                fnCallbackDraw(oSettings);
            }
        });

        $nPrevious.click(function() {
            if (!$(this).hasClass('dis-page')) {
                oSettings.oApi._fnPageChange(oSettings, 'previous');
                fnCallbackDraw(oSettings);
            }
        });

        $nNext.click(function() {
            if (!$(this).hasClass('dis-page')) {
                oSettings.oApi._fnPageChange(oSettings, 'next');
                fnCallbackDraw(oSettings);
            }
        });

        $nLast.click(function() {
            if (!$(this).hasClass('dis-page')) {
                oSettings.oApi._fnPageChange(oSettings, 'last');
                fnCallbackDraw(oSettings);
            }
        });
        $nInput.keyup(function(e) {
            if (e.which == 38 || e.which == 39) {
                this.value++;
            }
            else if ((e.which == 37 || e.which == 40) && this.value > 1) {
                this.value--;
            }
            if (this.value == false || this.value.match(/[^0-9]/))
            {
                $nInput[0].value = '';
                /* Nothing entered or non-numeric character */
                return;
            }
            var iNewStart = oSettings._iDisplayLength * (this.value - 1);
            if (iNewStart >= oSettings.fnRecordsDisplay()) {
                /* Display overrun */
                oSettings._iDisplayStart = (Math.ceil((oSettings.fnRecordsDisplay()) /
                  oSettings._iDisplayLength) - 1) * oSettings._iDisplayLength;
                fnCallbackDraw(oSettings);
            } else {
                oSettings._iDisplayStart = iNewStart;
                fnCallbackDraw(oSettings);
            }
        });

        $nInput.focusout(function() {
            var iPages = Math.ceil((oSettings.fnRecordsDisplay()) / oSettings._iDisplayLength);
            var iCurrentPage = Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength) + 1;
            this.value = iCurrentPage;
            self.handlerNavigation(iCurrentPage, iPages, $(oSettings.nTableWrapper));
        });
    },

    'fnUpdate': function(oSettings, fnCallbackDraw) {
        if (!oSettings.aanFeatures.p) {
            return;
        }
        var iPages = Math.ceil((oSettings.fnRecordsDisplay()) / oSettings._iDisplayLength);
        var iCurrentPage = Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength) + 1;
        this.handlerNavigation(iCurrentPage, iPages, $(oSettings.nTableWrapper));

        /* Loop over each instance of the pager */
        var an = oSettings.aanFeatures.p;
        for (var i = 0, iLen = an.length; i < iLen; i++) {
            var spans = an[i].getElementsByTagName('span');
            var inputs = an[i].getElementsByTagName('input');
            spans[3].innerHTML = ' of ' + iPages;
            inputs[0].value = iCurrentPage;
        }
    },
    handlerNavigation: function handler(value, iPages, $container) {
        if (value >= iPages) {
            $container.find('.last').addClass('dis-page');
            $container.find('.next').addClass('dis-page');
            $container.find('.first').removeClass('dis-page');
            $container.find('.previous').removeClass('dis-page');
        } else if (value == 1) {
            $container.find('.first').addClass('dis-page');
            $container.find('.previous').addClass('dis-page');
            $container.find('.last').removeClass('dis-page');
            $container.find('.next').removeClass('dis-page');
        } else {
            $container.find('.first').removeClass('dis-page');
            $container.find('.previous').removeClass('dis-page');
            $container.find('.last').removeClass('dis-page');
            $container.find('.next').removeClass('dis-page');
        }
    }
};
/**
 * @param {Object} oSettings
 */
$.fn.dataTableExt.oApi.fnStandingRedraw = function(oSettings) {
    if (oSettings.oFeatures.bServerSide === false) {
        var before = oSettings._iDisplayStart;
        oSettings.oApi._fnReDraw(oSettings);
        // iDisplayStart has been reset to zero - so lets change it back
        oSettings._iDisplayStart = before;
        oSettings.oApi._fnCalculateEnd(oSettings);
    }
    // draw the 'current' page
    oSettings.oApi._fnDraw(oSettings);
};
