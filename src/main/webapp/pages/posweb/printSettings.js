function save() {
    writeCookie("receiptPrinterId", getValue('localPrintSettings'), document, new Date(2099,11,31), context);
    alert(savedMessage);
}

Behaviour.register({
	'#localPrintSettings': function(select) {
	    setValue(select, readCookie("receiptPrinterId"));
	},
	
	'#submitButton': function(button) {
	    button.onclick = save;
	}
});