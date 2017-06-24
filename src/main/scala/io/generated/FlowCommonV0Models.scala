/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.3.28
 * apibuilder:0.12.3 https://api.apibuilder.io/flow/common/0.3.28/scala_models
 */
package io.flow.common.v0.models {

  sealed trait ExpandableOrganization extends _root_.scala.Product with _root_.scala.Serializable

  /**
   * Defines the valid discriminator values for the type ExpandableOrganization
   */
  sealed trait ExpandableOrganizationDiscriminator extends _root_.scala.Product with _root_.scala.Serializable

  object ExpandableOrganizationDiscriminator {

    case object Organization extends ExpandableOrganizationDiscriminator { override def toString = "organization" }
    case object OrganizationReference extends ExpandableOrganizationDiscriminator { override def toString = "organization_reference" }

    case class UNDEFINED(override val toString: String) extends ExpandableOrganizationDiscriminator

    val all = Seq(Organization, OrganizationReference)

    private[this] val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ExpandableOrganizationDiscriminator = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ExpandableOrganizationDiscriminator] = byName.get(value.toLowerCase)

  }

  sealed trait ExpandableUser extends _root_.scala.Product with _root_.scala.Serializable

  /**
   * Defines the valid discriminator values for the type ExpandableUser
   */
  sealed trait ExpandableUserDiscriminator extends _root_.scala.Product with _root_.scala.Serializable

  object ExpandableUserDiscriminator {

    case object User extends ExpandableUserDiscriminator { override def toString = "user" }
    case object UserReference extends ExpandableUserDiscriminator { override def toString = "user_reference" }

    case class UNDEFINED(override val toString: String) extends ExpandableUserDiscriminator

    val all = Seq(User, UserReference)

    private[this] val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ExpandableUserDiscriminator = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ExpandableUserDiscriminator] = byName.get(value.toLowerCase)

  }

  /**
   * Defines structured fields for address to be used in user/form input. Either text
   * or the structured input needs to be present.
   */
  case class Address(
    text: _root_.scala.Option[String] = None,
    streets: _root_.scala.Option[Seq[String]] = None,
    city: _root_.scala.Option[String] = None,
    province: _root_.scala.Option[String] = None,
    postal: _root_.scala.Option[String] = None,
    country: _root_.scala.Option[String] = None,
    latitude: _root_.scala.Option[String] = None,
    longitude: _root_.scala.Option[String] = None
  )

  case class CatalogItemSummary(
    number: String,
    name: String,
    attributes: Map[String, String]
  )

  /**
   * Defines structured fields for a contact person. Typically used for specifying
   * contact person for an account, shipment, or organization representative
   */
  case class Contact(
    name: io.flow.common.v0.models.Name,
    company: _root_.scala.Option[String] = None,
    email: _root_.scala.Option[String] = None,
    phone: _root_.scala.Option[String] = None
  )

  /**
   * A customer represents the actual person purchasing from you. This information is
   * needed primarily to support logistics (delivery to this person), fraud
   * management (verification of who the person is), and reporting. We also recommend
   * including your customer number whenever possible as doing so will allow Flow to
   * link up transactions for this customer making customer service easier.
   */
  case class Customer(
    name: io.flow.common.v0.models.Name,
    number: _root_.scala.Option[String] = None,
    phone: _root_.scala.Option[String] = None,
    email: _root_.scala.Option[String] = None
  )

  case class DatetimeRange(
    from: _root_.org.joda.time.DateTime,
    to: _root_.org.joda.time.DateTime
  )

  case class Dimension(
    depth: _root_.scala.Option[io.flow.common.v0.models.Measurement] = None,
    diameter: _root_.scala.Option[io.flow.common.v0.models.Measurement] = None,
    length: _root_.scala.Option[io.flow.common.v0.models.Measurement] = None,
    weight: _root_.scala.Option[io.flow.common.v0.models.Measurement] = None,
    width: _root_.scala.Option[io.flow.common.v0.models.Measurement] = None
  )

  case class Dimensions(
    product: _root_.scala.Option[io.flow.common.v0.models.Dimension] = None,
    packaging: _root_.scala.Option[io.flow.common.v0.models.Dimension] = None
  )

  /**
   * Represents an exception to the schedule of a center.
   */
  case class Exception(
    `type`: io.flow.common.v0.models.ExceptionType,
    datetimeRange: io.flow.common.v0.models.DatetimeRange
  )

  /**
   * Some important fields related to experiences used in various APIs
   */
  case class ExperienceSummary(
    id: String,
    key: String,
    name: String
  )

  /**
   * Line items represent the items a consumer is purchasing, including additional
   * information to complete the transaction. Note that you may pass in as many line
   * items as you like - including repeating item numbers across line items.
   */
  case class LineItem(
    number: String,
    quantity: Long,
    price: io.flow.common.v0.models.Money,
    attributes: Map[String, String],
    center: _root_.scala.Option[String] = None,
    discount: _root_.scala.Option[io.flow.common.v0.models.Money] = None
  )

  /**
   * Line items represent the items a consumer is purchasing, including additional
   * information to complete the transaction. Note that you may pass in as many line
   * items as you like - including repeating item numbers across line items.
   */
  case class LineItemForm(
    number: String,
    quantity: Long,
    price: _root_.scala.Option[io.flow.common.v0.models.Money] = None,
    attributes: _root_.scala.Option[Map[String, String]] = None,
    center: _root_.scala.Option[String] = None,
    discount: _root_.scala.Option[io.flow.common.v0.models.Money] = None
  )

  case class Margin(
    `type`: io.flow.common.v0.models.MarginType,
    value: BigDecimal
  )

  case class Measurement(
    value: String,
    units: io.flow.common.v0.models.UnitOfMeasurement
  )

  /**
   * Money represents an amount in a given currency
   */
  case class Money(
    amount: Double,
    currency: String
  )

  case class Name(
    first: _root_.scala.Option[String] = None,
    last: _root_.scala.Option[String] = None
  )

  /**
   * Represents a single organization in the system, and what environment it is
   * currently operating in.
   */
  case class Organization(
    id: String,
    name: String,
    environment: io.flow.common.v0.models.Environment,
    parent: _root_.scala.Option[io.flow.common.v0.models.OrganizationReference] = None
  ) extends ExpandableOrganization

  case class OrganizationReference(
    id: String
  ) extends ExpandableOrganization

  case class OrganizationSummary(
    id: String,
    name: String
  )

  /**
   * Represents an amount-currency pair for a basic price
   */
  case class Price(
    amount: Double,
    currency: String,
    label: String
  )

  /**
   * Amount-currency paid for a basic price to be used in model forms, before a price
   * label is created. Label in the main price model is created by the implementing
   * api.
   */
  case class PriceForm(
    amount: Double,
    currency: String
  )

  case class PriceWithBase(
    currency: String,
    amount: Double,
    label: String,
    base: _root_.scala.Option[io.flow.common.v0.models.Price] = None
  )

  case class Rounding(
    `type`: io.flow.common.v0.models.RoundingType,
    method: io.flow.common.v0.models.RoundingMethod,
    value: BigDecimal
  )

  /**
   * Represents operating calendar and holidays of a center
   */
  case class Schedule(
    calendar: _root_.scala.Option[io.flow.common.v0.models.Calendar] = None,
    holiday: io.flow.common.v0.models.HolidayCalendar,
    exception: Seq[io.flow.common.v0.models.Exception],
    cutoff: _root_.scala.Option[String] = None,
    minLeadTime: _root_.scala.Option[Long] = None,
    maxLeadTime: _root_.scala.Option[Long] = None
  )

  /**
   * Represents a single user in the system
   */
  case class User(
    id: String,
    email: _root_.scala.Option[String] = None,
    name: io.flow.common.v0.models.Name,
    status: io.flow.common.v0.models.UserStatus = io.flow.common.v0.models.UserStatus.Active
  ) extends ExpandableUser

  case class UserReference(
    id: String
  ) extends ExpandableUser

  case class Zone(
    province: _root_.scala.Option[String] = None,
    country: String
  )

  /**
   * Provides future compatibility in clients - in the future, when a type is added
   * to the union ExpandableOrganization, it will need to be handled in the client
   * code. This implementation will deserialize these future types as an instance of
   * this class.
   */
  case class ExpandableOrganizationUndefinedType(
    description: String
  ) extends ExpandableOrganization

  /**
   * Provides future compatibility in clients - in the future, when a type is added
   * to the union ExpandableUser, it will need to be handled in the client code. This
   * implementation will deserialize these future types as an instance of this class.
   */
  case class ExpandableUserUndefinedType(
    description: String
  ) extends ExpandableUser

  sealed trait Calendar extends _root_.scala.Product with _root_.scala.Serializable

  object Calendar {

    /**
     * Mon - Fri
     */
    case object Weekdays extends Calendar { override def toString = "weekdays" }
    /**
     * 7 days per week
     */
    case object Everyday extends Calendar { override def toString = "everyday" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends Calendar

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Weekdays, Everyday)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): Calendar = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[Calendar] = byName.get(value.toLowerCase)

  }

  sealed trait Capability extends _root_.scala.Product with _root_.scala.Serializable

  object Capability {

    case object Crossdock extends Capability { override def toString = "crossdock" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends Capability

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Crossdock)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): Capability = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[Capability] = byName.get(value.toLowerCase)

  }

  sealed trait ChangeType extends _root_.scala.Product with _root_.scala.Serializable

  object ChangeType {

    case object Insert extends ChangeType { override def toString = "insert" }
    case object Update extends ChangeType { override def toString = "update" }
    case object Delete extends ChangeType { override def toString = "delete" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends ChangeType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Insert, Update, Delete)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ChangeType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ChangeType] = byName.get(value.toLowerCase)

  }

  sealed trait DayOfWeek extends _root_.scala.Product with _root_.scala.Serializable

  object DayOfWeek {

    case object Sunday extends DayOfWeek { override def toString = "sunday" }
    case object Monday extends DayOfWeek { override def toString = "monday" }
    case object Tuesday extends DayOfWeek { override def toString = "tuesday" }
    case object Wednesday extends DayOfWeek { override def toString = "wednesday" }
    case object Thursday extends DayOfWeek { override def toString = "thursday" }
    case object Friday extends DayOfWeek { override def toString = "friday" }
    case object Saturday extends DayOfWeek { override def toString = "saturday" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends DayOfWeek

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): DayOfWeek = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[DayOfWeek] = byName.get(value.toLowerCase)

  }

  sealed trait DeliveredDuty extends _root_.scala.Product with _root_.scala.Serializable

  object DeliveredDuty {

    /**
     * Delivered Duty Paid (DDP). See https://en.wikipedia.org/wiki/Incoterms for more
     * information
     */
    case object Paid extends DeliveredDuty { override def toString = "paid" }
    /**
     * Delivered Duty Unpaid (DDU). See https://en.wikipedia.org/wiki/Incoterms for
     * more information
     */
    case object Unpaid extends DeliveredDuty { override def toString = "unpaid" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends DeliveredDuty

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Paid, Unpaid)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): DeliveredDuty = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[DeliveredDuty] = byName.get(value.toLowerCase)

  }

  sealed trait Environment extends _root_.scala.Product with _root_.scala.Serializable

  object Environment {

    /**
     * In sandbox, no external services (e.g. orders, payments, logistics) will
     * generate real transactions
     */
    case object Sandbox extends Environment { override def toString = "sandbox" }
    /**
     * In production, all external services are live and will generate real
     * transactions
     */
    case object Production extends Environment { override def toString = "production" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends Environment

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Sandbox, Production)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): Environment = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[Environment] = byName.get(value.toLowerCase)

  }

  sealed trait ExceptionType extends _root_.scala.Product with _root_.scala.Serializable

  object ExceptionType {

    case object Open extends ExceptionType { override def toString = "open" }
    case object Closed extends ExceptionType { override def toString = "closed" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends ExceptionType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Open, Closed)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ExceptionType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ExceptionType] = byName.get(value.toLowerCase)

  }

  sealed trait HolidayCalendar extends _root_.scala.Product with _root_.scala.Serializable

  object HolidayCalendar {

    case object UsBankHolidays extends HolidayCalendar { override def toString = "us_bank_holidays" }
    case object JewishHolidays extends HolidayCalendar { override def toString = "jewish_holidays" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends HolidayCalendar

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(UsBankHolidays, JewishHolidays)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): HolidayCalendar = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[HolidayCalendar] = byName.get(value.toLowerCase)

  }

  /**
   * Either a fixed or percent adjustment applied to some amount.
   */
  sealed trait MarginType extends _root_.scala.Product with _root_.scala.Serializable

  object MarginType {

    /**
     * A fixed amount to add, e.g 2.5 would add an absolute margin of 2.5 in the base
     * currency of your item.
     */
    case object Fixed extends MarginType { override def toString = "fixed" }
    /**
     * A percent of the base cost to add, e.g. 1.2 would represent a 1.2% increase.
     */
    case object Percent extends MarginType { override def toString = "percent" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends MarginType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Fixed, Percent)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): MarginType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[MarginType] = byName.get(value.toLowerCase)

  }

  sealed trait MeasurementSystem extends _root_.scala.Product with _root_.scala.Serializable

  object MeasurementSystem {

    case object Imperial extends MeasurementSystem { override def toString = "imperial" }
    case object Metric extends MeasurementSystem { override def toString = "metric" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends MeasurementSystem

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Imperial, Metric)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): MeasurementSystem = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[MeasurementSystem] = byName.get(value.toLowerCase)

  }

  /**
   * A user's role within an organization
   */
  sealed trait Role extends _root_.scala.Product with _root_.scala.Serializable

  object Role {

    case object Admin extends Role { override def toString = "admin" }
    case object Member extends Role { override def toString = "member" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends Role

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Admin, Member)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): Role = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[Role] = byName.get(value.toLowerCase)

  }

  /**
   * Direction to round localized price
   */
  sealed trait RoundingMethod extends _root_.scala.Product with _root_.scala.Serializable

  object RoundingMethod {

    /**
     * Round up to the specified rounding.value
     */
    case object Up extends RoundingMethod { override def toString = "up" }
    /**
     * Round down to the specified rounding.value
     */
    case object Down extends RoundingMethod { override def toString = "down" }
    /**
     * Round to the nearest specified rounding.value. If equidistant, will round up.
     */
    case object Nearest extends RoundingMethod { override def toString = "nearest" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends RoundingMethod

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Up, Down, Nearest)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): RoundingMethod = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[RoundingMethod] = byName.get(value.toLowerCase)

  }

  /**
   * Rounding type determines the rule used to enforce rounding. Common rules will
   * ensure that the final price ends w/ a given number (e.g. ends with .95) or is a
   * multiple of some number (multiples of 5 or 10 are common).
   */
  sealed trait RoundingType extends _root_.scala.Product with _root_.scala.Serializable

  object RoundingType {

    /**
     * Rounds a value to an amount ending with a pattern, e.g. an amount ending in
     * '.99'.
     */
    case object Pattern extends RoundingType { override def toString = "pattern" }
    /**
     * Rounds a value to an amount that is the multiple of a value, e.g. rounding to
     * the nearest 5 would ensure that the resulting number ends in either 0 or 5.
     */
    case object Multiple extends RoundingType { override def toString = "multiple" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends RoundingType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Pattern, Multiple)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): RoundingType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[RoundingType] = byName.get(value.toLowerCase)

  }

  /**
   * Pending better name
   */
  sealed trait ScheduleExceptionStatus extends _root_.scala.Product with _root_.scala.Serializable

  object ScheduleExceptionStatus {

    case object Open extends ScheduleExceptionStatus { override def toString = "Open" }
    case object Closed extends ScheduleExceptionStatus { override def toString = "Closed" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends ScheduleExceptionStatus

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Open, Closed)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ScheduleExceptionStatus = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ScheduleExceptionStatus] = byName.get(value.toLowerCase)

  }

  sealed trait SortDirection extends _root_.scala.Product with _root_.scala.Serializable

  object SortDirection {

    case object Ascending extends SortDirection { override def toString = "ascending" }
    case object Descending extends SortDirection { override def toString = "descending" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends SortDirection

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Ascending, Descending)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): SortDirection = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[SortDirection] = byName.get(value.toLowerCase)

  }

  /**
   * Defines the units of measurement that we support. As units are added, we conform
   * with the standard units provided by jscience as part of JSR 363 - see
   * http://jscience.org/api/javax/measure/unit/SI.html and
   * http://jscience.org/api/javax/measure/unit/NonSI.html
   */
  sealed trait UnitOfMeasurement extends _root_.scala.Product with _root_.scala.Serializable

  object UnitOfMeasurement {

    /**
     * Equivalent to MILLI(METRE).
     */
    case object Millimeter extends UnitOfMeasurement { override def toString = "millimeter" }
    /**
     * Equivalent to CENTI(METRE).
     */
    case object Centimeter extends UnitOfMeasurement { override def toString = "centimeter" }
    /**
     * A unit of length equal to 0.01004 m (standard name in).
     */
    case object Inch extends UnitOfMeasurement { override def toString = "inch" }
    /**
     * A unit of length equal to 0.3048 m (standard name ft).
     */
    case object Foot extends UnitOfMeasurement { override def toString = "foot" }
    /**
     * A unit of volume equal to one cubic inch (in³).
     */
    case object CubicInch extends UnitOfMeasurement { override def toString = "cubic_inch" }
    /**
     * The metric unit for volume quantities (m³).
     */
    case object CubicMeter extends UnitOfMeasurement { override def toString = "cubic_meter" }
    /**
     * A unit of mass equal to 1 / 1000 kilogram (standard name g).
     */
    case object Gram extends UnitOfMeasurement { override def toString = "gram" }
    /**
     * The base unit for mass quantities (kg).
     */
    case object Kilogram extends UnitOfMeasurement { override def toString = "kilogram" }
    /**
     * The base unit for length quantities (m).
     */
    case object Meter extends UnitOfMeasurement { override def toString = "meter" }
    /**
     * A unit of mass equal to 1 / 16 POUND (standard name oz).
     */
    case object Ounce extends UnitOfMeasurement { override def toString = "ounce" }
    /**
     * A unit of mass equal to 453.59237 grams (avoirdupois pound, standard name lb).
     */
    case object Pound extends UnitOfMeasurement { override def toString = "pound" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends UnitOfMeasurement

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Millimeter, Centimeter, Inch, Foot, CubicInch, CubicMeter, Gram, Kilogram, Meter, Ounce, Pound)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): UnitOfMeasurement = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[UnitOfMeasurement] = byName.get(value.toLowerCase)

  }

  /**
   * Defines the units of time that we support
   */
  sealed trait UnitOfTime extends _root_.scala.Product with _root_.scala.Serializable

  object UnitOfTime {

    case object Year extends UnitOfTime { override def toString = "year" }
    case object Month extends UnitOfTime { override def toString = "month" }
    case object Week extends UnitOfTime { override def toString = "week" }
    case object Day extends UnitOfTime { override def toString = "day" }
    case object Hour extends UnitOfTime { override def toString = "hour" }
    case object Minute extends UnitOfTime { override def toString = "minute" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends UnitOfTime

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Year, Month, Week, Day, Hour, Minute)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): UnitOfTime = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[UnitOfTime] = byName.get(value.toLowerCase)

  }

  /**
   * The status of the user account. Valid transitions are from pending -> active,
   * active -> inactive, inactive -> active
   */
  sealed trait UserStatus extends _root_.scala.Product with _root_.scala.Serializable

  object UserStatus {

    /**
     * Represents a new user account that is pending approval
     */
    case object Pending extends UserStatus { override def toString = "pending" }
    /**
     * Represents an active user account - can authenticate with this account
     */
    case object Active extends UserStatus { override def toString = "active" }
    /**
     * Represents an inactive user account - can no longer authenticate with this
     * account
     */
    case object Inactive extends UserStatus { override def toString = "inactive" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends UserStatus

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Pending, Active, Inactive)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): UserStatus = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[UserStatus] = byName.get(value.toLowerCase)

  }

  sealed trait ValueAddedService extends _root_.scala.Product with _root_.scala.Serializable

  object ValueAddedService {

    /**
     * See https://en.wikipedia.org/wiki/ORM-D
     */
    case object HazardousMaterial extends ValueAddedService { override def toString = "Hazardous Material" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends ValueAddedService

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(HazardousMaterial)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ValueAddedService = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ValueAddedService] = byName.get(value.toLowerCase)

  }

  sealed trait Visibility extends _root_.scala.Product with _root_.scala.Serializable

  object Visibility {

    case object Public extends Visibility { override def toString = "public" }
    case object Private extends Visibility { override def toString = "private" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends Visibility

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all = Seq(Public, Private)

    private[this]
    val byName = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): Visibility = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[Visibility] = byName.get(value.toLowerCase)

  }

}