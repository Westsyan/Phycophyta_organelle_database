var columns = [
    {
        field: "geneid",
        title: "Gene_ID",
        align:"center",
        halign:"center",
        valign:"middle",
        formatter: function (value, row) {
            return "<p><a href='/MRDB/genome/moreInfo?id=" + row.id + "' target='_blank'>" + row.geneid + "</a></p>";
        }
    }, {
        field: "chr",
        title: "Chr",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "start",
        title: "Start",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "end",
        title: "End",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "strand",
        title: "Strand",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "go",
        title: "GO",
        align:"center",
        halign:"center",
        valign:"middle",
        formatter: function (value, row) {
            var go = "";
            if (row.go !== "--") {
                var array = row.go.split(";");
                $.each(array, function (i, v) {
                    go += "<p><a href='http://amigo.geneontology.org/amigo/term/" + v + "' target='_blank'>" + v + "</a></p>"
                });
            } else {
                go = row.go
            }
            return go;
        }
    }, {
        field: "kegg",
        title: "KEGG",
        align: "center",
        halign: "center",
        valign: "middle",
        formatter: function (value, row) {
            var kegg = "";
            if (row.kegg !== "--") {
                var array = row.kegg.split(";");
                $.each(array, function (i, v) {
                    kegg += "<p><a href='https://www.kegg.jp/dbget-bin/www_bget?ko:" + v + "' target='_blank'>" + v + "</a></p>"
                });
            } else {
                kegg = row.kegg
            }
            return kegg;
        }
    }, {
        field: "cogClass",
        title: "COG Class",
        align: "center",
        halign: "center",
        valign: "middle"
    }, {
        field: "cogAnno",
        title: "COG Class Annotation",
        halign: "center",
        valign: "middle"
    }, {
        field: "kogClass",
        title: "KOG Class",
        align: "center",
        halign: "center",
        valign: "middle"
    }, {
        field: "kogAnno",
        title: "KOG Class Annotation",
        halign: "center",
        valign: "middle"
    }, {
        field: "pfam",
        title: "Pfam",
        halign: "center",
        valign: "middle"
    }, {
        field: "swiss",
        title: "Swissprot",
        halign: "center",
        valign: "middle"
    }, {
        field: "trembl",
        title: "TrEMBL",
        halign: "center",
        valign: "middle"
    }, {
        field: "nr",
        title: "nr",
        halign: "center",
        valign: "middle"
    }
];

function checkBox() {

    var array = ["Chr", "Strand", "Start", "End", "COG Class", "COG Class Annotation", "GO", "KEGG",
        "KOG Class", "KOG Class Annotation", "Pfam", "Swissprot", "TrEMBL", "nr"];
    var values = ["chr", "strand", "start", "end", "cogClass", "cogAnno", "go", "kegg", "kogClass",
        "kogAnno", "pfam", "swiss", "trembl", "nr"];
    var tHtml = "";
    $.each(array, function (i, v) {
        tHtml += "<th data-field='" + values[i] + "' data-sortable='true'>" + v + "</th>"
    });

    $("#marker").after(tHtml);

    var html = "";
    $.each(array, function (n, value) {
            html += "<label style='margin-right: 15px'>" +
                "<input type='checkbox' checked='checked' value='" + values[n] + "' onclick=\"setColumns('" + values[n] + "')\">" + value +
                "</label>"
        }
    );
    $("#checkbox").append(html);
}

function hidden() {
    var hiddenArray = ["cogAnno", "kogAnno", "pfam", "trembl", "nr"];

    $.each(hiddenArray, function (n, value) {
            $('#table').bootstrapTable('hideColumn', value);
            $("input:checkbox[value=" + value + "]").attr("checked", false)
        }
    );
}

function setColumns(value) {
    var element = $("input:checkbox[value=" + value + "]");
    if (element.is(":checked")) {
        $('#table').bootstrapTable('showColumn', value);
    } else {
        $('#table').bootstrapTable('hideColumn', value);
    }
}

/**
 * 
 * Browse jQuery 代码
 * 
 */
function Browse(url) {
    checkBox();

    $('#table').bootstrapTable({
        method: 'post',
        url: url,
        sidePagination: "server",
        pageNumber: 1,
        pagination: true,
        pageList: [10, 25],
        contentType: "application/x-www-form-urlencoded",
        columns: columns
    });

    hidden()
}

function formValidation() {
    $('#geneIdForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            geneId: {
                validators: {
                    notEmpty: {
                        message: 'Gene Id is required！'
                    }
                }
            }
        }
    });
    $('#regionForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            chr: {
                validators: {
                    notEmpty: {
                        message: 'Chromosome is required！'
                    }
                }
            },
            start: {
                validators: {
                    notEmpty: {
                        message: 'Start is required！'
                    },
                    integer: {
                        message: 'Start must be integer！'
                    }

                }
            },
            end: {
                validators: {
                    notEmpty: {
                        message: 'End is required！'
                    },
                    integer: {
                        message: 'End must be integer！'
                    }

                }
            }
        }
    });

}

function geneIdSearch() {
    var form = $("#geneIdForm")
    var fv = form.data("formValidation");
    fv.validate();
    if (fv.isValid()) {
        var index = layer.load(1, {
            shade: [0.1, '#fff']
        });
        $("#search").attr("disabled", true).html("Search...");
        $.ajax({
            url: "/MRDB/genome/searchById",
            type: "post",
            data: $("#geneIdForm").serialize(),
            success: function (data) {
                $('#table').bootstrapTable("load", data);
                $("#search").attr("disabled", false).html("Search").blur();
                layer.close(index);
                $("#result").show()
            }
        });
    }
}

function regionSearch() {
    var form = $("#regionForm")
    var fv = form.data("formValidation");
    fv.validate();
    if (fv.isValid()) {
        var index = layer.load(1, {
            shade: [0.1, '#fff']
        });
        $("#search").attr("disabled", true).html("Search...");
        $.ajax({
            url: "/MRDB/genome/searchByRegion",
            type: "post",
            data: $("#regionForm").serialize(),
            success: function (data) {
                $('#table').bootstrapTable("load", data);
                $("#search").attr("disabled", false).html("Search").blur();
                layer.close(index);
                $("#result").show()
            }
        });
    }
}

$('#egChr').click(function () {
    var eg = $(this).text().trim();
    $('#chr').val(eg);
    $("#regionForm").formValidation("revalidateField", "chr")
});

$('#egStart').click(function () {
    var eg = $(this).text().trim();
    $('#start').val(eg);
    $("#regionForm").formValidation("revalidateField", "start")
});

$('#egEnd').click(function () {
    var eg = $(this).text().trim();
    $('#end').val(eg);
    $("#regionForm").formValidation("revalidateField", "end")
});

function getChr() {
    $.ajax({
        url:"/MRDB/utils/getAllChr",
        type:"post",
        success:function (data) {
            $("#chr").select2({data:data})
        }
    })
}

/**
 *
 * 根据条件搜索 jQuery 代码
 * 
 */
function SearchTable() {
    AutoGene();

    egGene();

    hideResult();

    getChr();

    checkBox();

    $("#table").bootstrapTable({columns:columns});

    hidden();

    formValidation()
}
