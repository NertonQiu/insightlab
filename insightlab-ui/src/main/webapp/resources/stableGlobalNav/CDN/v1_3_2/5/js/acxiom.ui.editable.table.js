var oTablePET;
var aDataSetPET = [
	 ['Hello World','65.000','41.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'ended', '12:25:00'],
	 ['Go World','65.000','42.000', 'ended', '12:25:00'],
	 ['Go World','65.000','42.000', 'paused', '12:25:00'],
	 ['Hello World','65.000','41.000', 'paused', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Hello World','65.000','41.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00'],
	 ['Go World','65.000','42.000', 'active', '12:25:00']

];
var optionsPET = {
	"aaData": aDataSetPET,

	"aoColumnDefs": [ 
	  {"aTargets": [ 0 ],"sClass":"inline_editable"},
	  {"aTargets": [ 1 ],"sClass":"inline_editable"},
	  {"aTargets": [ 2 ],"sClass":"inline_editable"},
	  {"aTargets": [ 3 ],"sClass":"inline_editable"},
	  {"aTargets": [ 4 ],"sClass":"inline_editable"}
	 ],
	
	"bScrollInfinite": true,
	"bScrollCollapse": true,
	"sScrollY": "250px",
	"bPaginate": false,
	"aaSorting": [[0,'asc']],
	"bFilter": false	
};


function restoreRowPET ( oTablePET, nRow )
{
	var aData = oTablePET.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	jqTds.parents('tr').removeClass("selected_tr");	
	for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
		oTablePET.fnUpdate( aData[i], nRow, i, false );
	}			
	oTablePET.fnDraw();
}
function editRowPET ( oTablePET, nRow )
{	
    var aData = oTablePET.fnGetData(nRow);
    var jqTds = $('>td', nRow);
	jqTds.parents('tr').addClass("selected_tr");
    jqTds[0].innerHTML = '<input class="acxiom-input" type="text" value="'+aData[0]+'">';
    jqTds[1].innerHTML = '<div class="acxiom-select"><select><option>'+aData[1]+'</option></select></div>';
    jqTds[2].innerHTML = '<div class="acxiom-select"><select><option>'+aData[2]+'</option></select></div>';
    jqTds[3].innerHTML = '<div class="acxiom-select"><select><option seleced="seleced">active</option><option>ended</option><option>paused</option></select></div>';
    jqTds[4].innerHTML = '<input class="acxiom-input" type="text" value="'+aData[4]+'">';

}

function saveRowPET ( oTablePET, nRow )
{
    var jqInputs = $('input', nRow);
    oTablePET.fnUpdate( jqInputs[0].value, nRow, 0, false );
    oTablePET.fnUpdate( jqInputs[1].value, nRow, 1, false );
    oTablePET.fnUpdate( jqInputs[2].value, nRow, 2, false );
    oTablePET.fnUpdate( jqInputs[3].value, nRow, 3, false );
    oTablePET.fnUpdate( jqInputs[4].value, nRow, 4, false );
    oTablePET.fnDraw();
}

function init_example(elem_id){

	oTablePET = $("#"+elem_id).dataTable(optionsPET);
	
	//add inline edit
	var nEditing = null;

    $('#example').on('click', 'td.inline_editable', function (e) {
		
    	//$(this).unbind("click");
        e.preventDefault();
         
        /* Get the row as a parent of the link that was clicked on */
        var nRow = $(this).parents('tr')[0];
		        
        //todo 
        if( nEditing == nRow ){
        	return;
        }
        
        if ( nEditing !== null && nEditing != nRow ) {
            /* A different row is being edited - the edit should be cancelled and this row edited */
            restoreRowPET( oTablePET, nEditing );
            editRowPET( oTablePET, nRow );
            nEditing = nRow;
        }
        else if ( nEditing == nRow ) {
            /* This row is being edited and should be saved */
            saveRowPET( oTablePET, nEditing );
            nEditing = null;
        }
        else {
            /* No row currently being edited */
            editRowPET( oTablePET, nRow );
            nEditing = nRow;
        }
    } );

}


