var deletePaymentObligationsHiddenFields = function(attributeName, value) {
	var arrHiddenFields = document.getElementsByName(attributeName);
	for (var i = 0; i < arrHiddenFields.length; i++) {
		if (arrHiddenFields[i].value == value) {
			var _parent = arrHiddenFields[i].parentNode;
			_parent.removeChild(arrHiddenFields[i]); 
		}
	}
};

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchPaymentObligations";
		}
	},

	'img.paymentObligationDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editPaymentObligation";
		}
	},
		
	'img.removePaymentObligation': function(img) {
		setPointer(img);
		img.onclick = function() {
			var poRow = $('po_' + img.getAttribute('poid'));
			deletePaymentObligationsHiddenFields('paymentObligationIds', img.getAttribute('poid')); 
			var _parent = poRow.parentNode;
			_parent.removeChild(poRow);
			var _form = document.getElementsByName('acceptPaymentObligationPackForm');
			_form[0].submit();
		}
	},

	'#issuerId': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},
	
	'#acceptPaymentObligation':function(button){
		button.onclick = function(button) {
			setValue("selectIssuer", "false");
		}
	}
});

Event.observe(self, "load", function() {
	//sentences to paint rows to red...
	var _row;
	var acceptButton = $('acceptPaymentObligation');
	if (arrPaymentObligations != null && arrPaymentObligations.length > 0) {
		for (var i = 0; i < arrPaymentObligations.length; i++) {
			_row = document.getElementById("po_" + arrPaymentObligations[i]);
			for (var j = 0; j < _row.cells.length; j++) {				
				_row.cells[j].setAttribute("className","fieldDecoration");
				_row.cells[j].setAttribute("class","fieldDecoration");
			}
		}
		acceptButton.style.display = "none";
	} else {
		acceptButton.style.display = "block";
	}
});