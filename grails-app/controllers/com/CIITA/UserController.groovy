package com.CIITA

class UserController {

    static scaffold = User

    def search(){
        
    }
    def register() {
        if (request.method == "POST") {
            def user = new User(params)
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [ user: user ]
            }
        }
    }

    def results(String loginId){
        def users = User.where{ loginId =~ loginId}.list()
        return [users: users, term: params.loginId, totalUsers: User.count()]
    }

    def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }
}

class UserRegistrationCommand {
    def cryptoservice
    String getEncryptedPassword(){
        return cryptoService.getEncryptedPassword(password)
    }
    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress
    static constraints = {
        importFrom Profile
        importFrom User
        password(size: 6..8, blank: false, validator: { passwd, urc -> return passwd != urc.loginId})
        passwordRepeat(nullable: false, validator: { passwd2, urc -> return passwd2 == urc.password})
    }
}
