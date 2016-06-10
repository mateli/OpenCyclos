Behaviour.register({
	
	'#description': function(textarea) {
		new SizeLimit(textarea, 1024);
	},
	
	'#amount': function(input) {
		input.onkeypress = function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				$("username").focus();
			}
		}
	},
	
	'#principalType': function(select) {
		select.onchange = function(event) {
			self.location = context + "/do/webshop/doPayment?principalType=" + getValue(this);
		}
	},
	
	'#principal': function(input) {
		input.onkeypress = function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				$("_password").focus();
			}
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#cancelButton': function(button) {
		button.onclick = function() {
			self.location = context + "/do/webshop/cancel";
		}
	},
	
	'#virtualKeyboard': function(div) {
		var options = {
			'buttonStyle': 'width:30px;margin:1px;', 
			'submitLabel': null, 
			'capsLock': capsLockLabel, 
			'contrast': contrastLabel, 
			'clear': clearLabel
		};
		if (typeof chars == 'string') {
			options.chars = shuffle(chars.split("")).join("");
			options.buttonsPerRow = buttonsPerRow;
		}
		new VirtualKeyboard(div, "credentials", options);
	}	
});

function initFocus() {
	var amount = getObject("amount");
	if (amount) {
		amount.focus();
	} else {
		setFocus("principal");
	}
}

Event.observe(self, "load", function() {
	initFocus();
	setValue('principal', '');
	setValue('credentials', '');
});