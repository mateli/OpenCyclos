
Behaviour.register({
	'#cancelButton': function(button) {
		button.onclick = function() {
			self.location = context + (isPosWeb ? "/do/posweb/logout" : "/do/logout");
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var check = $('registrationAgreementCheck');
			if (check && ! check.checked) {
				alert(registrationAgreementNotCheckedMessage);
				return false;
			}
			return true;
		}
	},
	
	'#printAgreement': function(a) {
		setPointer(a);
		a.onclick = function() {
			var win = window.open("", "_blank");
			win.title = self.title;
			win.document.open();
			win.document.write("<html><head><title>" + agreementPrintTitle + "</title></head><body><div style='font-weight:bold;font-size:larger'>" + agreementPrintTitle + "</div><br>" + $('registrationAgreement').innerHTML + "</body></html>");
			win.document.close();
			(function() {
				win.print()
			}).delay(1);
		}
	}
});