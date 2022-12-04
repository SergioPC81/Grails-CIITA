package redsocial
import com.CIITA.*
import static java.util.Calendar.*
class BootStrap {

    def init = { servletContext ->
        environments{
            development{
                if(!Post.count()) createSampleData()
            }
            test{
                if(!Post.count()) createSampleData()
            }
        }
        createAdminUserIfRequired()
    }
    
    private createSampleData(){
        println "Creando datos de ejemplo"

        def now = new Date()
        def anotherDate = Calendar.getInstance()
        def chuck = new User(
                loginId: "chuck_norris",
                password: "highkick",
                profile: new Profile(fullName: "Chuck Norris", email: "chuck@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        def glen = new User(
                loginId: "glen",
                password: "sheldon",
                profile: new Profile(fullName: "Glen Smith", email: "glen@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        def peter = new User(
                loginId: "peter",
                password: "mandible",
                profile: new Profile(fullName: "Peter Ledbrook", email: "peter@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        def frankie = new User(
                loginId: "frankie",
                password: "testing",
                profile: new Profile(fullName: "Frankie Muniz", email: "frankie@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        def sara = new User(
                loginId: "sara",
                password: "crikey",
                profile: new Profile(fullName: "Sara Miles", email: "sara@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        def phil = new User(
                loginId: "phil",
                password: "thomas",
                profile: new Profile(fullName: "Phil Potts", email: "phil@nowhere.net"),
                dateCreated: now)
        def dillon = new User(loginId: "dillon",
                password: "crikey",
                profile: new Profile(fullName: "Dillon Jessop", email: "dillon@nowhere.net"),
                dateCreated: now).save(failOnError: true)
        chuck.addToFollowing(phil)
        chuck.addToPosts(content: "Been working my roundhouse kicks.")
        chuck.addToPosts(content: "Working on a few new moves. Bit sluggish today.")
        chuck.addToPosts(content: "Tinkering with the hubbub app.")
        chuck.save(failOnError: true)

        phil.addToFollowing(frankie)
        phil.addToFollowing(sara)
        phil.save(failOnError: true)

        phil.addToPosts(content: "Very first post")
        phil.addToPosts(content: "Second post")
        phil.addToPosts(content: "Time for a BBQ!")
        phil.addToPosts(content: "Writing a very very long book")
        phil.addToPosts(content: "Tap dancing")
        phil.addToPosts(content: "Pilates is killing me")
        phil.save(failOnError: true)

        sara.addToPosts(content: "My first post")
        sara.addToPosts(content: "Second post")
        sara.addToPosts(content: "Time for a BBQ!")
        sara.addToPosts(content: "Writing a very very long book")
        sara.addToPosts(content: "Tap dancing")
        sara.addToPosts(content: "Pilates is killing me")
        sara.save(failOnError: true)

        dillon.addToPosts(content: "Pilates is killing me as well")
        dillon.save(failOnError: true, flush: true)

        // Debemos actualizar el campo 'dateCreated' despues del primer guardado para
        // poder darle la vuelta a la característica 'auto-timestamping'. Sin embargo este truco
        // no funcionará en los campos 'lastUpdated'.
        def postsAsList = phil.posts as List
        postsAsList[0].addToTags(user: phil, name: "groovy")
        postsAsList[0].addToTags(user: phil, name: "grails")
        postsAsList[0].dateCreated = anotherDate.add(anotherDate.YEAR, -2)

        postsAsList[1].addToTags(user: phil, name: "grails")
        postsAsList[1].addToTags(user: phil, name: "ramblings")
        postsAsList[1].addToTags(user: phil, name: "second")
        postsAsList[1].dateCreated = anotherDate.add(anotherDate.YEAR, -3)

        postsAsList[2].addToTags(user: phil, name: "groovy")
        postsAsList[2].addToTags(user: phil, name: "bbq")
        postsAsList[2].dateCreated = anotherDate.add(anotherDate.YEAR, -1)

        postsAsList[3].addToTags(user: phil, name: "groovy")
        postsAsList[3].dateCreated = anotherDate.add(anotherDate.YEAR, -1)
        postsAsList[3].dateCreated = anotherDate.add(anotherDate.MONTH, 4)

        postsAsList[4].dateCreated = anotherDate.add(anotherDate.YEAR, -10)
        postsAsList[4].dateCreated = anotherDate.add(anotherDate.MONTH, 10)
        postsAsList[5].dateCreated = anotherDate.add(anotherDate.YEAR, -10)
        postsAsList[5].dateCreated = anotherDate.add(anotherDate.MONTH, 9)
        phil.save(failOnError: true)

        postsAsList = sara.posts as List
        postsAsList[0].dateCreated = anotherDate.add(anotherDate.YEAR, -14)
        postsAsList[0].dateCreated = anotherDate.add(anotherDate.MONTH, 5)
        postsAsList[1].dateCreated = anotherDate.add(anotherDate.YEAR, -15)
        postsAsList[1].dateCreated = anotherDate.add(anotherDate.MONTH, 4)
        postsAsList[2].dateCreated = anotherDate.add(anotherDate.YEAR, -15)
        postsAsList[2].dateCreated = anotherDate.add(anotherDate.MONTH, 8)
        postsAsList[3].dateCreated = anotherDate.add(anotherDate.YEAR, -11)
        postsAsList[3].dateCreated = anotherDate.add(anotherDate.MONTH, 8)
        postsAsList[4].dateCreated = anotherDate.add(anotherDate.YEAR, -11)
        postsAsList[4].dateCreated = anotherDate.add(anotherDate.MONTH, 9)
        postsAsList[5].dateCreated = anotherDate.add(anotherDate.YEAR, -11)
        postsAsList[5].dateCreated = anotherDate.add(anotherDate.MONTH, 11)
        
        sara.dateCreated = anotherDate.add(anotherDate.DATE, -2)
        sara.save(failOnError: true)

        dillon.dateCreated = anotherDate.add(anotherDate.DATE, -2)
        //dillon.save(failOnError: true, flush: true)
        dillon.save(failOnError: true)

    }

    private createAdminUserIfRequired() {
        println "Creando user admin"
        if (!User.findByLoginId("admin")) {
            println "Base de datos nueva. Creando usuario ADMIN."

            def profile = new Profile(email: "admin@yourhost.com", fullName: "Administrator")
            new User(loginId: "admin", password: "secret", profile: profile).save(failOnError: true)
        }
        else {
            println "Usuario admin existente, abortando creación"
        }
    }
}
