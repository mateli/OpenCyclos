Behaviour.register({
		'form': function(form) {
            form.onsubmit = function() {
                    return requestValidation(form);
            }
        },
        
    	'#backButton': function(button) {
    		button.onclick = function() {
    			self.location = pathPrefix + "/searchInfoTexts";
    		}
    	},
    	
    	'#modifyButton': function(button) {
    		button.onclick = function() {
    			enableFormFields.apply(button.form, []);
    		};
    	}
});