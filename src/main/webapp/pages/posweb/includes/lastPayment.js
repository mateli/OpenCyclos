Behaviour.register({
	'#printLastReceipt': function(a) {
		setPointer(a);
		a.onclick = function() {
		    var a = $("printLastReceipt");
			var lastId = a.getAttribute("paymentId");
			var scheduled = booleanValue(a.getAttribute("isScheduled"));
			printTransactionReceipt(lastId, scheduled);
		}
	},
	
	'#closePrint': function(button) {
		button.onclick = function() {
			$('printDiv').hide();
			$('formTable').show();
			initFocus(); 
		}
	}
});