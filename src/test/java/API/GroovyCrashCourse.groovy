package API

class GroovyCrashCourse {

    static def langs = ['Java', 'JavaScript', 'C#', 'Python']
    static def nums = [10, 15, 30]

    static void main(String[] args){

        println(langs[0])
        println(langs[-1])

        def jList =
                langs.stream().filter(lang -> lang.startsWith('J')).toList()

        langs.findAll{lang -> lang.startsWith('J')}
        langs.findAll{it.startsWith('J')}

        println(nums.max {it})
        println(nums.average() {it})
        println(nums.min {it})
    }
}
