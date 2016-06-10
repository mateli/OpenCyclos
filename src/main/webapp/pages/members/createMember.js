function setPostAction(action) {
	var postAction = this.elements["postAction"];
	if (postAction == null) {
		//Create the hidden if not found
		postAction = document.createElement("input");
		postAction.setAttribute("type", "hidden");
		postAction.setAttribute("name", "postAction");
		this.appendChild(postAction);
	}
	postAction.value = action;
}

function newCaptcha() {
	var image = $('captchaImage');
	if (image) {
		image.src = context + "/captcha?random=" + new Date().getTime() + "_" + (Math.random() * 9999999999);
		image.show();
		setValue('captcha', '');
	}
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'#assignBrokerCheck': function(check) {
		check.onclick = function() {
			var isChecked = check.checked;
			$$('tr.trBroker').each(function(tr) {
				Element[isChecked ? 'show' : 'hide'](tr);
			});
			if (isChecked) {
				$('brokerUsername').focus();
			} else {
				setValue('newBrokerId', '');
				setValue('brokerUsername', '');
				setValue('brokerName', '');
			}
		}
	},
	
	'#assignPasswordCheck': function(check) {
		check.onclick = function() {
			var isChecked = check.checked;
			$$('tr.trPassword').each(function(tr) {
				Element[isChecked ? 'show' : 'hide'](tr);
			});
			if (isChecked) {
				getObject('member(user).password').focus();
			} else {
				setValue('member(user).password', '');
				setValue('confirmPassword', '');
			}
		}
	},

	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", brokers:true}, 'newBrokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", brokers:true}, 'newBrokerId', 'brokerUsername', 'brokerName', 'comments');
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var check = $('registrationAgreementCheck');
			if (check && ! check.checked) {
				alert(registrationAgreementNotCheckedMessage);
				return false;
			}
			return requestValidation(form);
		}
	},
	
	'input': function(checkbox) {
		var prefix = "chk_hidden_";
		if (checkbox.id.indexOf(prefix) >= 0) {
			checkbox.onclick = function() {
				$('hidden_' + this.id.substring(prefix.length)).value = this.checked;
			}.bindAsEventListener(checkbox);
		}
	},
	
	'#saveAndNewButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'new');
	},
	
	'#saveAndOpenProfileButton': function(button) {
		button.onclick = setPostAction.bind(button.form, 'openProfile');
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
	},
	
	'#newCaptcha': function(a) {
		setPointer(a);
		a.onclick = function() {
			newCaptcha();
			setFocus('captcha');
		}
	}
});

Event.observe(self, "load", function() {
	(getObject("member(user).username") || getObject("member(name)")).focus();
	var assignBroker = $('assignBrokerCheck');
	if (assignBroker) {
		assignBroker.checked = false;
		assignBroker.onclick();
	}
	var assignPassword = $('assignPasswordCheck');
	if (assignPassword) {
		assignPassword.checked = false;
		assignPassword.onclick();
	}
	if (isPublic) {
		newCaptcha();
	}
	var pwd = getObject('member(user).password');
	if (pwd) {
		pwd.value = '';
		setValue('confirmPassword', '');
	}
})